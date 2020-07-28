package mini.oo20.lab15;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DataLoader {
    private List<String> stdIgnore = Arrays.asList(new String[]{"a", "able", "about", "all", "an", "and", "any", "are", "as", "at", "be", "been", "by",
            "can", "can't", "could", "couldn't", "do", "does", "doesn't", "don't", "down", "has", "hasn't", "have", "haven't", "he", "here", "his", "how",
            "I", "I'm", "if", "in", "is", "it", "its", "it's", "just", "like", "many", "much", "no", "not", "now", "of", "on", "one",
            "or", "she", "so", "than", "that", "the", "them", "then", "there", "these", "they", "this", "those", "to", "too", "up", "very", "was", "we", "were",
            "what", "when", "where", "which", "who", "will", "won't", "would", "you", "you'd", "you'll"}).stream().map(x -> x.toLowerCase()).collect(Collectors.toList());
    private AtomicBoolean done;

    public DataLoader() {
        done = new AtomicBoolean(false);
    }

    public void read(String dir, String pattern, BlockingQueue<List<String>> queue){
        while(!done.get()) {
            String word;
            File dire = new File(dir);
            File[] list = dire.listFiles();
            List<String> files = new ArrayList<>();
            for (File file : list) {
                if (file.isFile() && Pattern.matches(pattern, file.getName())) {
                    files.add(dir + "/" + file.getName());
                }
            }
            try {
                for (String file : files) {
                    int length = 1;
                    BufferedReader a = new BufferedReader(new InputStreamReader(new
                            FileInputStream(file)));
                    String str;
                    while ((str = a.readLine()) != null) {
                        str = str.toLowerCase();
                        Scanner s1 = new Scanner(new StringReader(str)).useDelimiter(" ");
                        while (s1.hasNext()) {
                            word = s1.next();
                            word = word.replaceAll("[\\,\\.\\(\\)\\;\\*]", "");
                            word = word.replaceAll("\"", "");
                            if (stdIgnore.contains(word) || word.equals("")) continue;
                            else {
                                List<String> outlist = new ArrayList<>(3);
                                outlist.add(word);
                                outlist.add(file);
                                outlist.add(String.valueOf(length));
                                queue.put(outlist);

                            }
                        }
                        length++;
                    }
                }
            } catch (IOException | InterruptedException a) {
                a.printStackTrace();
            }
        }

    }

    public void stop(){
        synchronized(this) {
            notify();
        }
        done.set(true);
    }

    public String load(String filename, int line) {
        try {
            BufferedReader a = new BufferedReader(new InputStreamReader(new
                    FileInputStream(filename)));
            String str;
            int length = 1;
            while ((str = a.readLine()) != null) {
                if (length != line) length++;
                else return str;

            }
        }
        catch (IOException a){
            a.printStackTrace();
        }
        return null;
    }
}

