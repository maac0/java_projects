package mini.oo20.lab15.test;

import mini.oo20.lab15.SearchEngine;
import org.junit.Assert;

import java.util.List;

public class SearchEngineTest {

    /***
     * Prosty equals() - sprawdzenie rozmiaru prostego indeksu
     * @difficulty 3
     */
    @org.junit.Test
    public void simpleSizeTest() {

        SearchEngine engine = new SearchEngine("data", "sample.txt", 1);

        engine.build();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        engine.stop();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(engine.getSize(), 78);

    }

    /***
     * Prosty equals() - sprawdzenie zawartości indeksu
     * @difficulty 2
     */
    @org.junit.Test
    public void simpleWordTest() {

        SearchEngine engine = new SearchEngine("data", "sample.txt", 1);

        engine.build();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        engine.stop();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<String> words = engine.getWords();

        Assert.assertNotNull(words);
        Assert.assertFalse(words.contains("are"));
        Assert.assertTrue(words.contains("singularity"));

    }

    /***
     * Prosty equals() - sprawdzenie nazwy pliku i linii
     * @difficulty 3
     */
    @org.junit.Test
    public void oneEntryTest() {

        SearchEngine engine = new SearchEngine("data", "sample.txt", 1);

        engine.build();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        engine.stop();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SearchEngine.Entry entry = engine.query("singularity");

        Assert.assertNotNull(entry);
        Assert.assertTrue(entry.getFilename().endsWith("sample.txt"));
        Assert.assertEquals(entry.getLine(), 5);
    }

    /***
     * Prosty equals() - sprawdzenie znalezionego tekstu
     * @difficulty 2
     */
    @org.junit.Test
    public void oneEntryTextTest() {

        SearchEngine engine = new SearchEngine("data", "sample.txt", 1);

        engine.build();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        engine.stop();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SearchEngine.Entry entry = engine.query("singularity");
        Assert.assertTrue(entry.getText().contains("singularity"));
        Assert.assertTrue(entry.getText().contains("inflationary"));
    }

    /***
     * Prosty equals() - sprawdzenie rozmiaru indeksu przy paru wątkach indexujących
     * @difficulty 2
     */
    @org.junit.Test
    public void manyBuildersTest() {

        SearchEngine engine = new SearchEngine("data", "sample.txt", 2);

        engine.build();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        engine.stop();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(engine.getSize(), 78);

    }


    /***
     * Prosty equals() - sprawdzenie rezultatu przy kilku wystąpieniach słów (w tym z dużej i małej litery)
     * @difficulty 3
     */
    @org.junit.Test
    public void manyEntriesTest() {

        SearchEngine engine = new SearchEngine("data", "data00[1-5].txt", 5);

        engine.build();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        engine.stop();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SearchEngine.Entry entry = engine.query("protons");

        Assert.assertNotNull(entry);
        Assert.assertTrue(entry.getFilename().endsWith("data001.txt"));
        Assert.assertTrue(entry.getText().contains("Protons"));

        Assert.assertTrue(engine.isStopped());

    }


    /***
     * Prosty assertFalse() - sprawdzenie rezultatu zapytania z dwoma słowami
     * @difficulty 2
     */
    @org.junit.Test
    public void composedQuery2Test() {

        SearchEngine engine = new SearchEngine("data", "data00[4-9].txt", 2);

        engine.build();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SearchEngine.Entry entry = engine.query("Big Bang");

        Assert.assertNotNull(entry);
        Assert.assertTrue(entry.getFilename().endsWith("data004.txt"));
        Assert.assertTrue(entry.getText().contains("Big Bang"));

        engine.stop();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(engine.isStopped());
    }

    /***
     * Prosty assertFalse() - sprawdzenie rezultatu zapytania z trzema słowami
     * @difficulty 2
     */
    @org.junit.Test
    public void composedQuery3Test() {

        SearchEngine engine = new SearchEngine("data", "data00[3-5].txt", 2);

        engine.build();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SearchEngine.Entry entry = engine.query("New York Times");

        Assert.assertNotNull(entry);
        Assert.assertTrue(entry.getFilename().endsWith("data004.txt"));
        Assert.assertTrue(entry.getText().contains("New York Times"));

        engine.stop();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(engine.isStopped());

    }

}