package mini.oo20.lab15;


import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SearchEngine {

    private Thread loaderThread;
    private Thread[] indexerThreads;
    private DataLoader loader;
    private InvertedIndex[] indexers;
    private BlockingQueue<List<String>> queue ;
    private Map<String, List<List<String>>> index;

    public SearchEngine(String dir, String pattern, int indexerNum) {

        // TODO: utwórz queue oraz index

        loader = new DataLoader();
        index = new HashMap<>();
        queue = new LinkedBlockingQueue<>();
        indexerNum = Math.max(1,indexerNum);

        indexers = new InvertedIndex[indexerNum];
        indexerThreads = new Thread[indexerNum];

        for (int i = 0; i<indexerNum; i++) {
            indexers[i] = new InvertedIndex(index);
        }

        loaderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                loader.read(dir, pattern, queue);
            }
        });

        for (int i = 0; i < indexerNum; i++) {
            int finalI = i;
            indexerThreads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    indexers[finalI].build(queue);
                }
            });
        }
    }

    public void build () {
        loaderThread.start();
        for (int i = 0; i < indexerThreads.length; i++) {
            indexerThreads[i].start();
        }
    }


    public Entry query(String words) {
        Entry entry =  indexers[0].query(words);
        if (entry == null) return null;
        entry.setText(loader.load(entry.getFilename(), entry.getLine()));
        return entry;
    }

    /**
     * zatrzymuje budowanie indeksu(poprzez zastopowanie wszystkich wątków)
     */
    public void stop() {
        loader.stop();
        for (int i = 0; i < indexerThreads.length; i++) {
            indexers[i].stop();
        }
    }

    public int getSize() {
        return index.size();
    }

    public List<String> getWords() {
        ArrayList<String> list = new ArrayList <>();
        list.addAll(index.keySet());
        Collections.sort(list);
        return list;
    }

    public boolean isRunning() {
        boolean isRunning =  !loaderThread.getState().equals(Thread.State.NEW) &&
                !loaderThread.getState().equals(Thread.State.TERMINATED);

        for (int i = 0; i < indexerThreads.length; i++) {
            isRunning = isRunning && !indexerThreads[i].getState().equals(Thread.State.NEW)
                    && !indexerThreads[i].getState().equals(Thread.State.TERMINATED);
        }

        return isRunning;
    }

    public boolean isStopped() {

        boolean isStopped =  loaderThread.getState().equals(Thread.State.TERMINATED);

        for (int i = 0; i < indexerThreads.length; i++) {
            isStopped = isStopped && indexerThreads[i].getState().equals(Thread.State.TERMINATED);
        }

        return isStopped;
    }

    static public class Entry {

        private String text;
        private String filename;
        private int line;


        public Entry(String filename, int line) {
            this.filename = filename;
            this.line = line;
        }

        public Entry(String filename, int line, String text) {
            this.filename = filename;
            this.line = line;
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public String getFilename() {
            return filename;
        }

        public int getLine() {
            return line;
        }

        public void setText(String text) {
            this.text = text;
        }

    }

}