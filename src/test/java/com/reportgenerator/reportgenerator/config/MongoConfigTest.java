package com.reportgenerator.reportgenerator.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MongoConfigTest {

    @Mock
    private MongodStarter mongodStarter;

    @Mock
    private MongodExecutable mongodExecutable;

    @InjectMocks
    private MongoConfig mongoConfig;

    private String url;

    private String dbName;

    private int port;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        mongoConfig = new MongoConfig();
        mongoConfig.url = "mongodb://localhost";
        mongoConfig.dbName = "report";
        mongoConfig.port = 27017;

    }

    @Test
    public void testMongodExecutableCreation() throws IOException {
        try (MockedStatic<MongodStarter> starterMockedStatic = mockStatic(MongodStarter.class)) {
            starterMockedStatic.when(MongodStarter::getDefaultInstance).thenReturn(mongodStarter);
            when(mongodStarter.prepare(any(ImmutableMongodConfig.class))).thenReturn(mongodExecutable);

            MongodExecutable result = mongoConfig.mongodExecutable();

            assertNotNull(result);
            verify(mongodStarter).prepare(any(ImmutableMongodConfig.class));
        }
    }

    @Test
    public void testMongoTemplateCreation() throws Exception {
        MongoClient mockMongoClient = mock(MongoClient.class);
        try (MockedStatic<MongoClients> mongoClientsMockedStatic = mockStatic(MongoClients.class)) {
            mongoClientsMockedStatic.when(() -> MongoClients.create(anyString())).thenReturn(mockMongoClient);

            MongoTemplate result = mongoConfig.mongoTemplate(mongodExecutable);

            assertNotNull(result);
            verify(mongodExecutable).start();
            mongoClientsMockedStatic.verify(() -> MongoClients.create("mongodb://localhost:27017"));
        }
    }

    @Test
    public void testMongodExecutableThrowsIOException() throws Exception {
        doThrow(new RuntimeException("Test")).when(mongodExecutable).start();

        assertThrows(Exception.class, () -> mongoConfig.mongoTemplate(mongodExecutable));

    }

    @Test
    public void testMongoTemplateThrowsException() throws Exception {
        doThrow(new IOException()).when(mongodExecutable).start();

        assertThrows(Exception.class, () -> mongoConfig.mongoTemplate(mongodExecutable));

        verify(mongodExecutable).start();
    }
}
