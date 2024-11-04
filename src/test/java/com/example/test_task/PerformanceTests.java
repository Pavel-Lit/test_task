package com.example.test_task;

import com.example.test_task.domain.SimpleEntity;
import com.example.test_task.repository.SimpleRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = "server.port=10000")
public class PerformanceTests {

    private static final int TOTAL_RECORDS = 100_000;
    private static final int TOTAL_REQUESTS = 1_000_000;
    private static final int THREAD_COUNT = 50;

    @Autowired
    private SimpleRepository repository;

    private static RestTemplate restTemplate;

    @BeforeAll
    static void setup() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void testBulkInsert() {
        List<SimpleEntity> simpleEntities = new ArrayList<>();
        for (int i = 0; i < TOTAL_RECORDS; i++) {
            SimpleEntity simpleEntity = new SimpleEntity();
            simpleEntity.setData("Data " + i);
            simpleEntities.add(simpleEntity);
        }
        repository.saveAll(simpleEntities);
        assertEquals(TOTAL_RECORDS, repository.count());
    }

    @Test
    public void testConcurrentRandomRead() throws InterruptedException {
        var entities = repository.findAll();
        var ids = new ArrayList<>(entities.stream().map(SimpleEntity::getId).toList());

        var executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        var futureResults = new ArrayList<Future<Long>>();
        var responseTimes = Collections.synchronizedList(new ArrayList<Long>());

        var startTime = System.currentTimeMillis();
        for (int i = 0; i < TOTAL_REQUESTS; i++) {
            var randomId = ids.get(ThreadLocalRandom.current().nextInt(ids.size()));
            futureResults.add(executorService.submit(() -> {
                var startRequestTime = System.nanoTime();

                var response = restTemplate.getForEntity("http://localhost:10000/api/simple/" + randomId, SimpleEntity.class);

                var endRequestTime = System.nanoTime();
                var duration = TimeUnit.NANOSECONDS.toMillis(endRequestTime - startRequestTime);
                responseTimes.add(duration);

                return response.getStatusCode().value() == 200 ? 0L : -1L;
            }));
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);

        var endTime = System.currentTimeMillis();
        var totalTime = endTime - startTime;

        responseTimes.sort(Long::compareTo);

        var median = responseTimes.get(responseTimes.size() / 2);
        var percentile95 = responseTimes.get((int) (responseTimes.size() * 0.95));
        var percentile99 = responseTimes.get((int) (responseTimes.size() * 0.99));

        System.out.println("Total Time: " + totalTime + " ms");
        System.out.println("Median Time: " + median + " ms");
        System.out.println("95th Percentile: " + percentile95 + " ms");
        System.out.println("99th Percentile: " + percentile99 + " ms");
    }
}
