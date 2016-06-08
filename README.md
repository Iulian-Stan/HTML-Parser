# HTMLParser

This aplication parses the **HTML** content downloaded by the [HTTPCrawler](https://github.com/Iulian-Stan/HTTPCrawler) 
implemented in the previous project. It pulls out the desired information such as **Title**, **META**, **HREF**s that 
allows to identify page's subject and other pages it is linked to.


## Implementation

It is based on the Java built-in [parser](https://docs.oracle.com/javase/7/docs/api/javax/swing/text/html/HTMLEditorKit.Parser.html)
from [HTMLEditorKit](https://docs.oracle.com/javase/7/docs/api/javax/swing/text/html/HTMLEditorKit.html) class.


It is the 3rd project of a series leading to the implementation of a **Web Robot**
 1. [DNSResolver](https://github.com/Iulian-Stan/DNSResolver) 
 2. [HTTPCrawler](https://github.com/Iulian-Stan/HTTPCrawler) 
 3. [HTMLParser](https://github.com/Iulian-Stan/HTMLParser)
 4. [WebRobot](https://github.com/Iulian-Stan/WebRobot)
