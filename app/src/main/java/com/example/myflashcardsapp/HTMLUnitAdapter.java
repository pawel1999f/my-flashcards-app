package com.example.myflashcardsapp;

import android.content.Context;
import android.content.res.Resources;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.example.myflashcardsapp.Flashcard;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeFilter;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

interface HTMLGetter {
    public void addElementsFromFile();
}





public class HTMLUnitAdapter implements HTMLGetter {

    //private WebClient webClient;

//    public void setWebClient(WebClient webClient) {
//        this.webClient = webClient;
//    }

    private Flashcard createFlashcard(String word) throws IOException {

        String url = "https://dictionary.cambridge.org/dictionary/english-polish/" + word;
        Document document = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
        System.out.println("1");
        //Elements answerers = document.select(".quick-results .quick-result-entry:has(.quick-result-option:contains(honey))" +
        // " .quick-result-overview li a:not(.sound-inline)");

        List<Element> answerers = document.select(".dsense:not(.dsense-noh) .trans");
        List<Element>  examples = document.select(".dsense:not(.dsense-noh) .examp .deg");

        if(answerers.size() == 0) {
            answerers = document.select(".dsense .trans");
            examples = document.select(".dsense .examp .deg");
        }
        int i1 = 4;
        int i2 = 4;

        if(answerers.size() < 4)
            i1 = answerers.size();
        if(examples.size() < 4)
            i2 = examples.size();

        answerers = answerers.subList(0, i1);
        examples = examples.subList(0, i2);

        String trans = "";
        List<String> sents = new ArrayList<>();

        for (Element answerer : answerers) {
            trans += answerer.text() + " ";
        }

        //:not(.dsense-noh)
        for (Element example : examples) {
            sents.add(example.text());
        }

        return new Flashcard(-1, word, trans, sents, 0);
    }



    public void addElementsFromFile() {
        Thread downloadThread = new Thread() {
            public void run() {
                try {

                    //List<Flashcard> flashcards = new ArrayList<Flashcard>();
                    Flashcard[] flashcards = new Flashcard[1000];
                    System.out.println(flashcards.length);
                    int index = 0;

                    Resources res = MyApplication.getAppContext().getResources();
                    InputStream fis = res.openRawResource(R.raw.words);
                    InputStreamReader inputStreamReader =
                            new InputStreamReader(fis, StandardCharsets.UTF_8);
                    try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                        String line = reader.readLine();
                        while (line != null) {
                            if(line.equals(""))
                                break;

                            System.out.println(index);
                            flashcards[index] = createFlashcard(line);
                            line = reader.readLine();
                            index++;
                        }
                    } catch (IOException e) {
                        // Error occurred when opening raw file for reading.
                    }

                    DatabaseFacade.getInstance().addFlashcard(flashcards);


                } finally {

                }
            }

        };
        downloadThread.start();

        System.out.println("2");



//        try (final WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED)) {
//////            XPathFactory xPathFactory = XPathFactory.newInstance();
//////            XPath xPath = xPathFactory.newXPath();
//////            String pageURL = "https://pl.bab.la/slownik/angielski-polski/honey";
//////            String xPathExpr = "//div[@id='quick-result']/div[@class='quick-result-entry']/li[text()]";
//////            String text = xPath.evaluate(xPathExpr, new InputSource(pageURL));
//////            System.out.println(text);
//            webClient.getOptions().setJavaScriptEnabled(true);
//            webClient.getOptions().setThrowExceptionOnScriptError(false);
//            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
//            final HtmlPage page = webClient.getPage("https://www.google.com");
//
//            //get list of all divs
//            //final List<?> divs = page.getByXPath("//div");
//
//            //get div which has a 'id' attribute of 'banner'
//            final List<?> texts = page.getByXPath("//div[@id='quick-result']/div[@class='quick-result-entry']/li[text()]");
//
//            for (Object text: texts) {
//                System.out.print(text + " ");
//            }
//            System.out.println();
//
//
//            String url = "https://pl.bab.la/slownik/angielski-polski/honey";
//            Document document = Jsoup.connect(url).get();
//
//            Elements answerers = document.select(".quick-result .quick-result-entry li");
//            for (Element answerer : answerers) {
//                System.out.println("Answerer: " + answerer.text());
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
