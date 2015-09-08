package my;

import javax.swing.text.html.HTMLEditorKit;

class ParserGetter extends HTMLEditorKit
{
  private static final long serialVersionUID = 1L;

  // exposes the protected method of the HTMLEditorKit class
  public HTMLEditorKit.Parser getParser()
  {
    return super.getParser();
  }
}
