package android.support.p000v4.text.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p000v4.util.PatternsCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.text.util.Linkify.MatchFilter;
import android.text.util.Linkify.TransformFilter;
import android.webkit.WebView;
import android.widget.TextView;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: android.support.v4.text.util.LinkifyCompat */
public final class LinkifyCompat {
    private static final Comparator<LinkSpec> COMPARATOR = new Comparator<LinkSpec>() {
        public /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
            return compare((LinkSpec) obj, (LinkSpec) obj2);
        }

        public final int compare(LinkSpec linkSpec, LinkSpec linkSpec2) {
            LinkSpec a = linkSpec;
            LinkSpec b = linkSpec2;
            LinkSpec linkSpec3 = a;
            LinkSpec linkSpec4 = b;
            if (a.start < b.start) {
                return -1;
            }
            if (a.start > b.start) {
                return 1;
            }
            if (a.end < b.end) {
                return 1;
            }
            if (a.end <= b.end) {
                return 0;
            }
            return -1;
        }
    };
    private static final String[] EMPTY_STRING = new String[0];

    /* renamed from: android.support.v4.text.util.LinkifyCompat$LinkSpec */
    private static class LinkSpec {
        int end;
        URLSpan frameworkAddedSpan;
        int start;
        String url;

        LinkSpec() {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.text.util.LinkifyCompat$LinkifyMask */
    public @interface LinkifyMask {
    }

    public static final boolean addLinks(@NonNull Spannable spannable, int i) {
        Spannable text = spannable;
        int mask = i;
        Spannable spannable2 = text;
        int i2 = mask;
        if (mask == 0) {
            return false;
        }
        URLSpan[] uRLSpanArr = (URLSpan[]) text.getSpans(0, text.length(), URLSpan.class);
        URLSpan[] old = uRLSpanArr;
        for (int i3 = uRLSpanArr.length - 1; i3 >= 0; i3--) {
            text.removeSpan(old[i3]);
        }
        if ((mask & 4) != 0) {
            boolean frameworkReturn = Linkify.addLinks(text, 4);
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = arrayList;
        if ((mask & 1) != 0) {
            ArrayList arrayList3 = arrayList;
            Pattern pattern = PatternsCompat.AUTOLINK_WEB_URL;
            String[] strArr = new String[3];
            strArr[0] = "http://";
            strArr[1] = "https://";
            strArr[2] = "rtsp://";
            gatherLinks(arrayList3, text, pattern, strArr, Linkify.sUrlMatchFilter, null);
        }
        if ((mask & 2) != 0) {
            Pattern pattern2 = PatternsCompat.AUTOLINK_EMAIL_ADDRESS;
            String[] strArr2 = new String[1];
            strArr2[0] = "mailto:";
            gatherLinks(arrayList2, text, pattern2, strArr2, null, null);
        }
        if ((mask & 8) != 0) {
            gatherMapLinks(arrayList2, text);
        }
        pruneOverlaps(arrayList2, text);
        if (arrayList2.size() == 0) {
            return false;
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            LinkSpec linkSpec = (LinkSpec) it.next();
            LinkSpec link = linkSpec;
            if (linkSpec.frameworkAddedSpan == null) {
                applyLink(link.url, link.start, link.end, text);
            }
        }
        return true;
    }

    public static final boolean addLinks(@NonNull TextView textView, int i) {
        TextView text = textView;
        int mask = i;
        TextView textView2 = text;
        int i2 = mask;
        if (mask == 0) {
            return false;
        }
        CharSequence text2 = text.getText();
        CharSequence t = text2;
        if (!(text2 instanceof Spannable)) {
            SpannableString valueOf = SpannableString.valueOf(t);
            SpannableString s = valueOf;
            if (!addLinks((Spannable) valueOf, mask)) {
                return false;
            }
            addLinkMovementMethod(text);
            text.setText(s);
            return true;
        } else if (!addLinks((Spannable) t, mask)) {
            return false;
        } else {
            addLinkMovementMethod(text);
            return true;
        }
    }

    public static final void addLinks(@NonNull TextView textView, @NonNull Pattern pattern, @Nullable String str) {
        TextView text = textView;
        Pattern pattern2 = pattern;
        String scheme = str;
        TextView textView2 = text;
        Pattern pattern3 = pattern2;
        String str2 = scheme;
        addLinks(text, pattern2, scheme, (String[]) null, (MatchFilter) null, (TransformFilter) null);
    }

    public static final void addLinks(@NonNull TextView textView, @NonNull Pattern pattern, @Nullable String str, @Nullable MatchFilter matchFilter, @Nullable TransformFilter transformFilter) {
        TextView text = textView;
        Pattern pattern2 = pattern;
        String scheme = str;
        MatchFilter matchFilter2 = matchFilter;
        TransformFilter transformFilter2 = transformFilter;
        TextView textView2 = text;
        Pattern pattern3 = pattern2;
        String str2 = scheme;
        MatchFilter matchFilter3 = matchFilter2;
        TransformFilter transformFilter3 = transformFilter2;
        addLinks(text, pattern2, scheme, (String[]) null, matchFilter2, transformFilter2);
    }

    public static final void addLinks(@NonNull TextView textView, @NonNull Pattern pattern, @Nullable String str, @Nullable String[] strArr, @Nullable MatchFilter matchFilter, @Nullable TransformFilter transformFilter) {
        TextView text = textView;
        Pattern pattern2 = pattern;
        String defaultScheme = str;
        String[] schemes = strArr;
        MatchFilter matchFilter2 = matchFilter;
        TransformFilter transformFilter2 = transformFilter;
        TextView textView2 = text;
        Pattern pattern3 = pattern2;
        String str2 = defaultScheme;
        String[] strArr2 = schemes;
        MatchFilter matchFilter3 = matchFilter2;
        TransformFilter transformFilter3 = transformFilter2;
        SpannableString valueOf = SpannableString.valueOf(text.getText());
        SpannableString spannable = valueOf;
        boolean addLinks = addLinks((Spannable) valueOf, pattern2, defaultScheme, schemes, matchFilter2, transformFilter2);
        boolean z = addLinks;
        if (addLinks) {
            text.setText(spannable);
            addLinkMovementMethod(text);
        }
    }

    public static final boolean addLinks(@NonNull Spannable spannable, @NonNull Pattern pattern, @Nullable String str) {
        Spannable text = spannable;
        Pattern pattern2 = pattern;
        String scheme = str;
        Spannable spannable2 = text;
        Pattern pattern3 = pattern2;
        String str2 = scheme;
        return addLinks(text, pattern2, scheme, (String[]) null, (MatchFilter) null, (TransformFilter) null);
    }

    public static final boolean addLinks(@NonNull Spannable spannable, @NonNull Pattern pattern, @Nullable String str, @Nullable MatchFilter matchFilter, @Nullable TransformFilter transformFilter) {
        Spannable spannable2 = spannable;
        Pattern pattern2 = pattern;
        String scheme = str;
        MatchFilter matchFilter2 = matchFilter;
        TransformFilter transformFilter2 = transformFilter;
        Spannable spannable3 = spannable2;
        Pattern pattern3 = pattern2;
        String str2 = scheme;
        MatchFilter matchFilter3 = matchFilter2;
        TransformFilter transformFilter3 = transformFilter2;
        return addLinks(spannable2, pattern2, scheme, (String[]) null, matchFilter2, transformFilter2);
    }

    public static final boolean addLinks(@NonNull Spannable spannable, @NonNull Pattern pattern, @Nullable String str, @Nullable String[] strArr, @Nullable MatchFilter matchFilter, @Nullable TransformFilter transformFilter) {
        Spannable spannable2 = spannable;
        Pattern pattern2 = pattern;
        String defaultScheme = str;
        String[] schemes = strArr;
        MatchFilter matchFilter2 = matchFilter;
        TransformFilter transformFilter2 = transformFilter;
        Spannable spannable3 = spannable2;
        Pattern pattern3 = pattern2;
        String defaultScheme2 = defaultScheme;
        String[] schemes2 = schemes;
        MatchFilter matchFilter3 = matchFilter2;
        TransformFilter transformFilter3 = transformFilter2;
        if (defaultScheme == null) {
            defaultScheme2 = "";
        }
        if (schemes == null || schemes.length < 1) {
            schemes2 = EMPTY_STRING;
        }
        String[] strArr2 = new String[(schemes2.length + 1)];
        String[] schemesCopy = strArr2;
        strArr2[0] = defaultScheme2.toLowerCase(Locale.ROOT);
        for (int index = 0; index < schemes2.length; index++) {
            String scheme = schemes2[index];
            schemesCopy[index + 1] = scheme != null ? scheme.toLowerCase(Locale.ROOT) : "";
        }
        boolean hasMatches = false;
        Matcher m = pattern2.matcher(spannable2);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            boolean allowed = true;
            if (matchFilter2 != null) {
                allowed = matchFilter2.acceptMatch(spannable2, start, end);
            }
            if (allowed) {
                String makeUrl = makeUrl(m.group(0), schemesCopy, m, transformFilter2);
                String str2 = makeUrl;
                applyLink(makeUrl, start, end, spannable2);
                hasMatches = true;
            }
        }
        return hasMatches;
    }

    private static void addLinkMovementMethod(@NonNull TextView textView) {
        TextView t = textView;
        TextView textView2 = t;
        MovementMethod movementMethod = t.getMovementMethod();
        MovementMethod m = movementMethod;
        if ((movementMethod == null || !(m instanceof LinkMovementMethod)) && t.getLinksClickable()) {
            t.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    private static String makeUrl(@NonNull String str, @NonNull String[] strArr, Matcher matcher, @Nullable TransformFilter transformFilter) {
        String url = str;
        String[] prefixes = strArr;
        Matcher matcher2 = matcher;
        TransformFilter filter = transformFilter;
        String url2 = url;
        String[] strArr2 = prefixes;
        Matcher matcher3 = matcher2;
        TransformFilter transformFilter2 = filter;
        if (filter != null) {
            url2 = filter.transformUrl(matcher2, url);
        }
        boolean hasPrefix = false;
        int i = 0;
        while (true) {
            if (i >= prefixes.length) {
                break;
            }
            if (!url2.regionMatches(true, 0, prefixes[i], 0, prefixes[i].length())) {
                i++;
            } else {
                hasPrefix = true;
                if (!url2.regionMatches(false, 0, prefixes[i], 0, prefixes[i].length())) {
                    url2 = prefixes[i] + url2.substring(prefixes[i].length());
                }
            }
        }
        if (!hasPrefix && prefixes.length > 0) {
            url2 = prefixes[0] + url2;
        }
        return url2;
    }

    private static void gatherLinks(ArrayList<LinkSpec> arrayList, Spannable spannable, Pattern pattern, String[] strArr, MatchFilter matchFilter, TransformFilter transformFilter) {
        ArrayList<LinkSpec> links = arrayList;
        Spannable s = spannable;
        Pattern pattern2 = pattern;
        String[] schemes = strArr;
        MatchFilter matchFilter2 = matchFilter;
        TransformFilter transformFilter2 = transformFilter;
        ArrayList<LinkSpec> arrayList2 = links;
        Spannable spannable2 = s;
        Pattern pattern3 = pattern2;
        String[] strArr2 = schemes;
        MatchFilter matchFilter3 = matchFilter2;
        TransformFilter transformFilter3 = transformFilter2;
        Matcher m = pattern2.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            int end2 = end;
            if (matchFilter2 == null || matchFilter2.acceptMatch(s, start, end)) {
                LinkSpec spec = new LinkSpec();
                spec.url = makeUrl(m.group(0), schemes, m, transformFilter2);
                spec.start = start;
                spec.end = end2;
                boolean add = links.add(spec);
            }
        }
    }

    private static void applyLink(String str, int i, int i2, Spannable spannable) {
        String url = str;
        int start = i;
        int end = i2;
        Spannable text = spannable;
        String str2 = url;
        int i3 = start;
        int i4 = end;
        Spannable spannable2 = text;
        text.setSpan(new URLSpan(url), start, end, 33);
    }

    private static final void gatherMapLinks(ArrayList<LinkSpec> arrayList, Spannable spannable) {
        ArrayList<LinkSpec> links = arrayList;
        Spannable s = spannable;
        ArrayList<LinkSpec> arrayList2 = links;
        Spannable spannable2 = s;
        String string = s.toString();
        int base = 0;
        while (true) {
            try {
                String findAddress = WebView.findAddress(string);
                String address = findAddress;
                if (findAddress != null) {
                    int indexOf = string.indexOf(address);
                    int start = indexOf;
                    if (indexOf < 0) {
                        break;
                    }
                    LinkSpec spec = new LinkSpec();
                    int length = start + address.length();
                    int end = length;
                    spec.start = base + start;
                    spec.end = base + length;
                    string = string.substring(length);
                    base += end;
                    try {
                        spec.url = "geo:0,0?q=" + URLEncoder.encode(address, "UTF-8");
                        boolean add = links.add(spec);
                    } catch (UnsupportedEncodingException e) {
                        UnsupportedEncodingException unsupportedEncodingException = e;
                    }
                } else {
                    break;
                }
            } catch (UnsupportedOperationException e2) {
                UnsupportedOperationException unsupportedOperationException = e2;
                return;
            }
        }
    }

    private static final void pruneOverlaps(ArrayList<LinkSpec> arrayList, Spannable spannable) {
        ArrayList<LinkSpec> links = arrayList;
        Spannable text = spannable;
        ArrayList<LinkSpec> arrayList2 = links;
        Spannable spannable2 = text;
        URLSpan[] urlSpans = (URLSpan[]) text.getSpans(0, text.length(), URLSpan.class);
        for (int i = 0; i < urlSpans.length; i++) {
            LinkSpec linkSpec = new LinkSpec();
            LinkSpec spec = linkSpec;
            linkSpec.frameworkAddedSpan = urlSpans[i];
            spec.start = text.getSpanStart(urlSpans[i]);
            spec.end = text.getSpanEnd(urlSpans[i]);
            boolean add = links.add(spec);
        }
        Collections.sort(links, COMPARATOR);
        int len = links.size();
        int i2 = 0;
        while (i2 < len - 1) {
            LinkSpec a = (LinkSpec) links.get(i2);
            LinkSpec b = (LinkSpec) links.get(i2 + 1);
            int remove = -1;
            if (a.start <= b.start && a.end > b.start) {
                if (b.end <= a.end) {
                    remove = i2 + 1;
                } else if (a.end - a.start > b.end - b.start) {
                    remove = i2 + 1;
                } else if (a.end - a.start < b.end - b.start) {
                    remove = i2;
                }
                if (remove != -1) {
                    URLSpan uRLSpan = ((LinkSpec) links.get(remove)).frameworkAddedSpan;
                    URLSpan span = uRLSpan;
                    if (uRLSpan != null) {
                        text.removeSpan(span);
                    }
                    Object remove2 = links.remove(remove);
                    len--;
                }
            }
            i2++;
        }
    }

    private LinkifyCompat() {
    }
}
