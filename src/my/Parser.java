package my;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.text.html.HTMLEditorKit;

public class Parser
{
  public static Map<String, Set<String>> gMap = null; // stores the key information from the page

  public static void main(String[] args)
  {
    try
    {
      File configFile = new File("config.properties");
      InputStream inputStream = new FileInputStream(configFile);
      Properties props = new Properties();
      props.load(inputStream);
      String encoding = props.getProperty("encoding", "ISO-8859-1");
      String url = props.getProperty("url", "http://iulian-stan.host22.com");
      String file = props.getProperty("file", "out.txt");
      gMap = parse(url, encoding, file);
      displyEntries(gMap);
    } catch (IOException e)
    {
      System.err.println(e);
    }
  }

  // initializes the parser and calls its main method returning the map
  private static Map<String, Set<String>> parse(String adr, String encoding, String file)
      throws IOException
  {
    ParserGetter kit = new ParserGetter();
    HTMLEditorKit.Parser parser = kit.getParser();
    URL url = new URL(adr);
    InputStream in = url.openStream();
    InputStreamReader r = new InputStreamReader(in, encoding);
    Map<String, Set<String>> lMap = new HashMap<String, Set<String>>();
    CallBacks callback = new CallBacks(adr, file, lMap);
    parser.parse(r, callback, true);
    return lMap;
  }

  // auxiliary function that displays map's content
  private static void displyEntries(Map<String, Set<String>> map)
  {
    if (map != null)
      for (Map.Entry<String, Set<String>> me : map.entrySet())
      {
        System.out.print("Key : " + me.getKey() + " - value(s) : ");
        for (String value : me.getValue())
          System.out.print(value + "\n\t\t\t");
        System.out.println();
      }
  }
}
