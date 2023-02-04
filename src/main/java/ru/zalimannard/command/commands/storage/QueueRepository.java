package ru.zalimannard.command.commands.storage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QueueRepository {
    public List<QueueEntity> getAll(String guildId) {
        return null;
    }

    public QueueEntity get(String guildId, int index) {
        return null;
    }

    public void save(String guildId, QueueEntity queueEntity) throws IOException {
        List<QueueEntity> queues = new ArrayList<>();
        queues.add(queueEntity);
        write(guildId, queues);
    }

    public void remove(String guildId, int index) {

    }

    private List<QueueEntity> read(String guildId) {
        return new ArrayList<>();
    }

    private void write(String guildId, List<QueueEntity> queueEntities) throws IOException {
        FileWriter writer = new FileWriter("queues-" + guildId + ".txt", false);
        writer.write(queueEntities.size() + "\n");
        for (int i = 0; i < queueEntities.size(); ++i) {
            writer.write(queueEntities.get(i).getTitle() + "\n");
            writer.write(queueEntities.get(i).getDescription() + "\n");
            writer.write(queueEntities.get(i).size() + "\n");
            for (int j = 0; j < queueEntities.get(i).size(); ++j) {
                writer.write(queueEntities.get(i).getTrackEntity(j).getTitle() + "\n");
                writer.write(queueEntities.get(i).getTrackEntity(j).getUrl() + "\n");
            }
        }
        writer.flush();
        writer.close();
    }
}
