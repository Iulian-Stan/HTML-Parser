package my;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

class CallBacks extends HTMLEditorKit.ParserCallback
{
  private boolean flagTitle = false; // title tag flag used by handleText
  private boolean flagText = false; // body tag flag used by handleText
  private String url = null; // current page url
  private BufferedWriter output = null;
  private Map<String, Set<String>> map = null;

  public CallBacks(String url, String file, Map<String, Set<String>> map) throws IOException
  {
    output = new BufferedWriter(new FileWriter(new File(file), false));
    this.url = (url.endsWith("/") ? url : url + '/');
    this.map = map;
  }

  // extracts start tags with its attributes and passes them to analysis - listAttributes
  public void handleStartTag(HTML.Tag tag, MutableAttributeSet attributes, int position)
  {
    if (tag.toString().toLowerCase().equals("title"))
      flagTitle = !flagTitle;
    if (tag.toString().toLowerCase().equals("body"))
      flagText = !flagText;
    this.listAttributes(attributes, tag);
  }

  // extract single tags with its attributes and passes them to analysis - listAttributes
  public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attributes, int position)
  {
    this.listAttributes(attributes, tag);
  }

  // extracts the text of the title tag or the body and writes it into a file or stores it in the
  // map
  public void handleText(char[] data, int position)
  {
    if (flagTitle)
    {
      addEntry(map, "TITLE", new String(data));
      flagTitle = !flagTitle;
    }
    if (flagText)
      try
      {
        output.write(data);
        output.newLine();
        output.flush();
      } catch (IOException e)
      {
        e.printStackTrace();
      }
  }

  // closes the stream when the parsing ends
  public void flush()
  {
    try
    {
      flagText = !flagText;
      output.close();
    } catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  // analyses the tags and its attributes passed by the callback methods
  private void listAttributes(AttributeSet attributes, HTML.Tag tag)
  {
    if (tag == HTML.Tag.A)
    {
      Object atr = attributes.getAttribute(HTML.Attribute.HREF);
      if (atr != null && atr.toString().indexOf('#') < 0)
      {
        String value = atr.toString();
        if (value.startsWith("/"))
          value = url + value.substring(1);
        else if (value.indexOf('/') < value.indexOf('.') && !value.contains("//")
            && !value.startsWith("www") || value.indexOf('.') < 0)
          value = url + value;
        addEntry(map, "HREF", value);
      }
    }
    if (tag == HTML.Tag.META)
    {
      Object atr = attributes.getAttribute(HTML.Attribute.NAME);
      if (atr != null)
      {
        String key = atr.toString().toUpperCase();
        if (key.equals("KEYWORDS") || key.equals("DESCRIPTION") || key.equals("ROBOTS"))
        {
          atr = attributes.getAttribute(HTML.Attribute.CONTENT);
          if (atr != null)
          {
            String value = atr.toString();
            addEntry(map, key, value);
          }
        }
      }
    }
  }

  // auxiliary function that adds records to the map
  public void addEntry(Map<String, Set<String>> map, String key, String value)
  {
    if (map.containsKey(key))
      map.get(key).add(value);
    else
    {
      Set<String> list = new HashSet<String>();
      list.add(value);
      map.put(key, list);
    }
  }
}
