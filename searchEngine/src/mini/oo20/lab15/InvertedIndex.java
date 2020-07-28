package mini.oo20.lab15;


import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class InvertedIndex {
    public Map<String, List<List<String>>> index = new HashMap<>();
    private AtomicBoolean done;
    public InvertedIndex(Map<String, List<List<String>>> index) {
        this.index = index;
        done = new AtomicBoolean(false);
    }

    public void build(BlockingQueue<List<String>> queue)  {
        while (!done.get()) {

                    List<String> temp;
                    try {
                        synchronized (queue){
                            temp = queue.take();
                            if(temp.get(0) == null){
                                try{
                                    queue.wait();
                                }
                                catch(InterruptedException a){
                                    a.printStackTrace();
                                }
                                continue;
                            }
                        }
                        String key = temp.get(0);
                        if(key == null) wait();
                        List<String> location = new ArrayList<>(2);
                        location.add(temp.get(1));
                        location.add(temp.get(2));
                        if (index.containsKey(key)) {
                            index.get(key).add(location);
                        } else {
                            index.put(key, new ArrayList<List<String>>());
                            index.get(key).add(location);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

    public synchronized SearchEngine.Entry query(String words) {
        Scanner s = new Scanner(words).useDelimiter(" ");
        List<String> wordlist = new ArrayList<String>();
        while (s.hasNext()) {
            wordlist.add(s.next().toLowerCase());
        }
        s.close();
        if (wordlist.size() == 0) return null;
        else if (wordlist.size() == 1) {
            String word = wordlist.get(0);
            List<String> file = new ArrayList<>();
            List<String> lines = new ArrayList<>();
            List<List<String>> seq1;
            if(index.containsKey(word)) seq1 = index.get(word);
            else return null;
            for (int i = 0; i < seq1.size(); i++) {
                file.add(seq1.get(i).get(0));
                lines.add(seq1.get(i).get(1));
            }
            String maxValue = mode(file);
            for (int i = 0; i < file.size(); i++) {
                if (file.get(i).equals(maxValue))
                    return new SearchEngine.Entry(maxValue, Integer.parseInt(lines.get(0)));
            }
        } else {
            List<String> file = new ArrayList<>();
            List<String> lines = new ArrayList<>();
            List<List<List<String>>> seq1 = new ArrayList<>();
            for (String a : wordlist) {
                if(index.containsKey(a)) seq1.add(index.get(a));
                else return null;
            }
            // Add each element of list into the set
            Set<List<String>> result = new LinkedHashSet<>(seq1.get(0));
            for (int i = 1; i < wordlist.size(); i++) {
                Set<List<String>> result1 = result.stream().distinct().filter(seq1.get(i)::contains).collect(Collectors.toSet());
                result = result1;
                for (List<String> strings : result) {
                    file.add(strings.get(0));
                    lines.add(strings.get(1));
                }
            }
            String maxValue = mode(file);
            for (int i = 0; i < file.size(); i++) {
                if (file.get(i).equals(maxValue)) {
                    return new SearchEngine.Entry(maxValue, Integer.parseInt(lines.get(0)));
                }
            }
        }
        return null;
    }

    public String mode(List<String> filee){
        String maxValue = null;
        int maxCount = 0;
        for (int i = 0; i < filee.size(); ++i)
        {
            int count = 0;
            for (int j = 0; j < filee.size(); ++j)
            {
                if (filee.get(j) == filee.get(i))
                    ++count;
            }
            if (count > maxCount)
            {
                maxCount = count;
                maxValue = filee.get(i);
            }
        }
        return maxValue;
    }
    public void stop(){
        synchronized(this) {
            notify();
        }
            done.set(true);
    }

    public class Location {

        public Location(String filename, int line) {
            this.fileName = filename;
            this.lines = new ArrayList<Integer>();
            lines.add(line);
        }

        public Location(String filename, List<Integer> lines) {
            this.fileName = filename;
            this.lines = lines;
        }


        public boolean equals(Object o) {
            if (o.getClass() != this.getClass()) return false;

            Location l = (Location)o;

            return (this.fileName.equals(l.fileName)) && (Objects.equals(this.lines, l.lines));
        }

        public Location intersection(Location l) {
            if (!this.fileName.equals(l.fileName)) return null;

            List<Integer> lines = new ArrayList<Integer>();

            for (Integer n: this.getLines()) {
                if (l.getLines().contains(n)) lines.add(n);
            }

            if (lines.isEmpty()) return null;

            Location nl = new Location(this.fileName, lines);
            return nl;
        }

        public String getFileName() {
            return fileName;
        }

        public List<Integer> getLines() {
            return lines;
        }

        public void addLine(int line) {
            this.lines.add(line);
        }

        private String fileName;
        private List<Integer> lines;
    }

}