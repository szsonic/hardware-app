package com.utils;

import freemarker.template.TemplateTransformModel;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author 李世成
 * @email: lishicheng@innjia.com
 * @date: 2017/8/24
 */
public class XmlEscape implements TemplateTransformModel {
    private static final char[] LT = "&lt;".toCharArray();
    private static final char[] GT = "&gt;".toCharArray();
    private static final char[] AMP = "&amp;".toCharArray();
    private static final char[] QUOT = "&quot;".toCharArray();
    private static final char[] APOS = "&apos;".toCharArray();

    public XmlEscape() {
    }

    public Writer getWriter(final Writer out, Map args) {
        return new Writer() {
            public void write(int c) throws IOException {
                switch(c) {
                    case 34:
                        out.write(QUOT, 0, 6);
                        break;
                    case 38:
                        out.write(AMP, 0, 5);
                        break;
                    case 39:
                        out.write(APOS, 0, 6);
                        break;
                    case 60:
                        out.write(LT, 0, 4);
                        break;
                    case 62:
                        out.write(GT, 0, 4);
                        break;
                    default:
                        out.write(c);
                }

            }

            public void write(char[] cbuf, int off, int len) throws IOException {
                int lastoff = off;
                int lastpos = off + len;

                int remaining;
                for(remaining = off; remaining < lastpos; ++remaining) {
                    switch(cbuf[remaining]) {
                        case '"':
                            out.write(cbuf, lastoff, remaining - lastoff);
                            out.write(QUOT, 0, 6);
                            lastoff = remaining + 1;
                            break;
                        case '&':
                            out.write(cbuf, lastoff, remaining - lastoff);
                            out.write(AMP, 0, 5);
                            lastoff = remaining + 1;
                            break;
                        case '\'':
                            out.write(cbuf, lastoff, remaining - lastoff);
                            out.write(APOS, 0, 6);
                            lastoff = remaining + 1;
                            break;
                        case '<':
                            out.write(cbuf, lastoff, remaining - lastoff);
                            out.write(LT, 0, 4);
                            lastoff = remaining + 1;
                            break;
                        case '>':
                            out.write(cbuf, lastoff, remaining - lastoff);
                            out.write(GT, 0, 4);
                            lastoff = remaining + 1;
                    }
                }

                remaining = lastpos - lastoff;
                if (remaining > 0) {
                    out.write(cbuf, lastoff, remaining);
                }

            }

            public void flush() throws IOException {
                out.flush();
            }

            public void close() {
            }
        };
    }
}
