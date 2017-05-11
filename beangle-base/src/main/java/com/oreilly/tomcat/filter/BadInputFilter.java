// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 
// Source File Name:   BadInputFilter.java

package com.oreilly.tomcat.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.*;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BadInputFilter
    implements Filter
{

    public BadInputFilter()
    {
        escapeQuotes = false;
        escapeAngleBrackets = false;
        escapeJavaScript = false;
        quotesHashMap = new HashMap();
        angleBracketsHashMap = new HashMap();
        javaScriptHashMap = new HashMap();
        allow = null;
        allows = new Pattern[0];
        denies = new Pattern[0];
        deny = null;
        parameterEscapes = new HashMap();
        quotesHashMap.put("\"", "&quot;");
        quotesHashMap.put("'", "&#39;");
        quotesHashMap.put("`", "&#96;");
        angleBracketsHashMap.put("<", "&lt;");
        angleBracketsHashMap.put(">", "&gt;");
        javaScriptHashMap.put("document(.*)\\.(.*)cookie", "document&#46;&#99;ookie");
        javaScriptHashMap.put("eval(\\s*)\\(", "eval&#40;");
        javaScriptHashMap.put("setTimeout(\\s*)\\(", "setTimeout$1&#40;");
        javaScriptHashMap.put("setInterval(\\s*)\\(", "setInterval$1&#40;");
        javaScriptHashMap.put("execScript(\\s*)\\(", "exexScript$1&#40;");
        javaScriptHashMap.put("(?i)javascript(?-i):", "javascript&#58;");
    }

    public boolean getEscapeQuotes()
    {
        return escapeQuotes;
    }

    public void setEscapeQuotes(boolean escapeQuotes)
    {
        this.escapeQuotes = escapeQuotes;
        if(escapeQuotes)
            parameterEscapes.putAll(quotesHashMap);
    }

    public boolean getEscapeAngleBrackets()
    {
        return escapeAngleBrackets;
    }

    public void setEscapeAngleBrackets(boolean escapeAngleBrackets)
    {
        this.escapeAngleBrackets = escapeAngleBrackets;
        if(escapeAngleBrackets)
            parameterEscapes.putAll(angleBracketsHashMap);
    }

    public boolean getEscapeJavaScript()
    {
        return escapeJavaScript;
    }

    public void setEscapeJavaScript(boolean escapeJavaScript)
    {
        this.escapeJavaScript = escapeJavaScript;
        if(escapeJavaScript)
            parameterEscapes.putAll(javaScriptHashMap);
    }

    public String getAllow()
    {
        return allow;
    }

    public void setAllow(String allow)
    {
        this.allow = allow;
        allows = precalculate(allow);
        servletContext.log((new StringBuilder("BadInputFilter: allow = ")).append(deny).toString());
    }

    public String getDeny()
    {
        return deny;
    }

    public void setDeny(String deny)
    {
        this.deny = deny;
        denies = precalculate(deny);
        servletContext.log((new StringBuilder("BadInputFilter: deny = ")).append(deny).toString());
    }

    public void init(FilterConfig filterConfig)
        throws ServletException
    {
        servletContext = filterConfig.getServletContext();
        setAllow(filterConfig.getInitParameter("allow"));
        setDeny(filterConfig.getInitParameter("deny"));
        String initParam = filterConfig.getInitParameter("escapeQuotes");
        if(initParam != null)
        {
            boolean flag = Boolean.parseBoolean(initParam);
            setEscapeQuotes(flag);
        }
        initParam = filterConfig.getInitParameter("escapeAngleBrackets");
        if(initParam != null)
        {
            boolean flag = Boolean.parseBoolean(initParam);
            setEscapeAngleBrackets(flag);
        }
        initParam = filterConfig.getInitParameter("escapeJavaScript");
        if(initParam != null)
        {
            boolean flag = Boolean.parseBoolean(initParam);
            setEscapeJavaScript(flag);
        }
        servletContext.log((new StringBuilder(String.valueOf(toString()))).append(" initialized.").toString());
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException
    {
        if(!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse))
        {
            filterChain.doFilter(request, response);
            return;
        }
        if(processAllowsAndDenies(request, response))
        {
            filterParameters(request);
            filterChain.doFilter(request, response);
        }
    }

    public boolean processAllowsAndDenies(ServletRequest request, ServletResponse response)
        throws IOException, ServletException
    {
        Map paramMap = request.getParameterMap();
        for(Iterator y = paramMap.keySet().iterator(); y.hasNext();)
        {
            String name = (String)y.next();
            String values[] = request.getParameterValues(name);
            if(!checkAllowsAndDenies(name, response))
                return false;
            if(values != null)
            {
                for(int i = 0; i < values.length; i++)
                {
                    String value = values[i];
                    if(!checkAllowsAndDenies(value, response))
                        return false;
                }

            }
        }

        return true;
    }

    public boolean checkAllowsAndDenies(String property, ServletResponse response)
        throws IOException, ServletException
    {
        if(denies.length == 0 && allows.length == 0)
            return true;
        for(int i = 0; i < denies.length; i++)
        {
            Matcher m = denies[i].matcher(property);
            if(m.find() && (response instanceof HttpServletResponse))
            {
                HttpServletResponse hres = (HttpServletResponse)response;
                hres.sendError(403);
                return false;
            }
        }

        for(int i = 0; i < allows.length; i++)
        {
            Matcher m = allows[i].matcher(property);
            if(m.find())
                return true;
        }

        if(denies.length > 0 && allows.length == 0)
            return true;
        if(response instanceof HttpServletResponse)
        {
            HttpServletResponse hres = (HttpServletResponse)response;
            hres.sendError(403);
        }
        return false;
    }

    public void filterParameters(ServletRequest request)
    {
        Map paramMap = ((HttpServletRequest)request).getParameterMap();
        try
        {
            if(setLockedMethod == null)
                setLockedMethod = paramMap.getClass().getMethod("setLocked", new Class[] {
                    Boolean.TYPE
                });
            setLockedMethod.invoke(paramMap, new Object[] {
                Boolean.FALSE
            });
        }
        catch(Exception e)
        {
            servletContext.log("BadInputFilter: Cannot filter parameters!");
        }
        for(Iterator escapesIterator = parameterEscapes.keySet().iterator(); escapesIterator.hasNext();)
        {
            String patternString = (String)escapesIterator.next();
            Pattern pattern = Pattern.compile(patternString);
            String paramNames[] = (String[])paramMap.keySet().toArray(STRING_ARRAY);
            for(int i = 0; i < paramNames.length; i++)
            {
                String name = paramNames[i];
                String values[] = ((HttpServletRequest)request).getParameterValues(name);
                Matcher matcher = pattern.matcher(name);
                boolean nameMatch = matcher.matches();
                if(nameMatch)
                {
                    String newName = matcher.replaceAll((String)parameterEscapes.get(patternString));
                    paramMap.remove(name);
                    paramMap.put(newName, values);
                    servletContext.log((new StringBuilder("Parameter name ")).append(name).append(" matched pattern \"").append(patternString).append("\".  Remote addr: ").append(((HttpServletRequest)request).getRemoteAddr()).toString());
                }
                if(values != null)
                {
                    for(int j = 0; j < values.length; j++)
                    {
                        String value = values[j];
                        matcher = pattern.matcher(value);
                        boolean valueMatch = matcher.find();
                        if(valueMatch)
                        {
                            String newValue = matcher.replaceAll((String)parameterEscapes.get(patternString));
                            values[j] = newValue;
                            servletContext.log((new StringBuilder("Parameter \"")).append(name).append("\"'s value \"").append(value).append("\" matched pattern \"").append(patternString).append("\".  Remote addr: ").append(((HttpServletRequest)request).getRemoteAddr()).toString());
                        }
                    }

                }
            }

        }

        try
        {
            if(setLockedMethod == null)
                setLockedMethod = paramMap.getClass().getMethod("setLocked", new Class[] {
                    Boolean.TYPE
                });
            setLockedMethod.invoke(paramMap, new Object[] {
                Boolean.TRUE
            });
        }
        catch(Exception exception) { }
    }

    public String toString()
    {
        return "BadInputFilter";
    }

    public void destroy()
    {
    }

    protected Pattern[] precalculate(String list)
    {
        if(list == null)
            return new Pattern[0];
        list = list.trim();
        if(list.length() < 1)
            return new Pattern[0];
        list = (new StringBuilder(String.valueOf(list))).append(",").toString();
        ArrayList reList = new ArrayList();
        int comma;
        for(; list.length() > 0; list = list.substring(comma + 1))
        {
            comma = list.indexOf(',');
            if(comma < 0)
                break;
            String pattern = list.substring(0, comma).trim();
            try
            {
                reList.add(Pattern.compile(pattern));
            }
            catch(PatternSyntaxException e)
            {
                IllegalArgumentException iae = new IllegalArgumentException((new StringBuilder("Syntax error in request filter pattern")).append(pattern).toString());
                iae.initCause(e);
                throw iae;
            }
        }

        Pattern reArray[] = new Pattern[reList.size()];
        return (Pattern[])reList.toArray(reArray);
    }

    protected static String info = "com.oreilly.tomcat.filter.BadInputFilter/2.0";
    private static final String STRING_ARRAY[] = new String[0];
    protected boolean escapeQuotes;
    protected boolean escapeAngleBrackets;
    protected boolean escapeJavaScript;
    protected HashMap quotesHashMap;
    protected HashMap angleBracketsHashMap;
    protected HashMap javaScriptHashMap;
    protected String allow;
    protected Pattern allows[];
    protected Pattern denies[];
    protected String deny;
    protected HashMap parameterEscapes;
    protected ServletContext servletContext;
    protected Method setLockedMethod;

}
