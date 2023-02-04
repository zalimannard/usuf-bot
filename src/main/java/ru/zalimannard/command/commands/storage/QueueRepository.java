package ru.zalimannard.command.commands.storage;

import ru.zalimannard.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QueueRepository {
    public List<QueueEntity> getAll(String guildId) throws IOException {
        return read(guildId);
    }

    public QueueEntity get(String guildId, int index) throws IOException {
        List<QueueEntity> queues = getAll(guildId);
        if ((index >= 0) && (index < queues.size())) {
            return getAll(guildId).get(index);
        } else {
            throw new IllegalArgumentException("Неправильный номер очереди");
        }
    }

    public void save(String guildId, QueueEntity queueEntity) throws IOException {
        List<QueueEntity> queues = new ArrayList<>();
        try {
            queues = read(guildId);
        } catch (IOException e) {
            // Если файла с сохранениями нет, то пусть. Просто создадим его
        }
        // Для затирания предыдущей с таким же названием
        boolean isExist = false;
        List<QueueEntity> clonedQueues = new ArrayList<>();
        for (int i = 0; i < queues.size(); ++i) {
            if (queues.get(i).getTitle().equals(queueEntity.getTitle())) {
                clonedQueues.add(queueEntity);
                isExist = true;
            } else {
                clonedQueues.add(queues.get(i));
            }
        }
        if (!isExist) {
            clonedQueues.add(queueEntity);
        }

        write(guildId, clonedQueues);
    }

    public void remove(String guildId, int index) throws IOException {
        List<QueueEntity> queues = read(guildId);
        queues.remove(index);
        write(guildId, queues);
    }

    private List<QueueEntity> read(String guildId) throws IOException {
        File file = new File("/opt/usuf-bot/queues/queues-" + guildId + ".txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<QueueEntity> queues = new ArrayList<>();

        int nQueue = Integer.parseInt(reader.readLine());
        for (int i = 0; i < nQueue; ++i) {
            String queueTitle = reader.readLine();
            String queueDescription = reader.readLine();
            String queueDuration = reader.readLine();
            int nTrack = Integer.parseInt(reader.readLine());
            QueueEntity queueEntity = new QueueEntity(queueTitle, queueDescription, new Duration(queueDuration));
            for (int j = 0; j < nTrack; ++j) {
                String trackTitle = reader.readLine();
                String trackUrl = reader.readLine();
                TrackEntity track = new TrackEntity(trackTitle, trackUrl);
                queueEntity.addTrackEntity(track);
            }
            queues.add(queueEntity);
        }
        reader.close();
        return queues;
    }

    private void write(String guildId, List<QueueEntity> queueEntities) throws IOException {
        File file = new File("/opt/usuf-bot/queues/queues-" + guildId + ".txt");
        FileWriter writer = new FileWriter(file);
        writer.write(queueEntities.size() + "\n");
        for (int i = 0; i < queueEntities.size(); ++i) {
            writer.write(queueEntities.get(i).getTitle() + "\n");
            writer.write(queueEntities.get(i).getDescription() + "\n");
            writer.write(queueEntities.get(i).getDuration().getHmsFormat() + "\n");
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
