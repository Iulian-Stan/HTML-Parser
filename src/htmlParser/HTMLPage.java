package htmlParser;

import java.util.HashSet;
import java.util.Set;

import urlParser.URL;

public class HTMLPage {
	public boolean toIndex;
	public boolean toFollow;
	public URL url;
	public String cachedFile;
	public Set<String> words;
	public Set<URL> links;
	
	public HTMLPage(URL url)
	{
		this.url = url;
		words = new HashSet<String>();
		links = new HashSet<URL>();
	}
}
