package android.support.graphics.drawable;

import android.graphics.Path;
import android.util.Log;
import java.util.ArrayList;

class PathParser {
    private static final String LOGTAG = "PathParser";

    private static class ExtractFloatResult {
        int mEndPosition;
        boolean mEndWithNegOrDot;

        ExtractFloatResult() {
        }
    }

    public static class PathDataNode {
        float[] params;
        char type;

        PathDataNode(char c, float[] fArr) {
            float[] params2 = fArr;
            float[] fArr2 = params2;
            this.type = (char) c;
            this.params = params2;
        }

        PathDataNode(PathDataNode pathDataNode) {
            PathDataNode n = pathDataNode;
            PathDataNode pathDataNode2 = n;
            this.type = (char) n.type;
            this.params = PathParser.copyOfRange(n.params, 0, n.params.length);
        }

        public static void nodesToPath(PathDataNode[] pathDataNodeArr, Path path) {
            PathDataNode[] node = pathDataNodeArr;
            Path path2 = path;
            PathDataNode[] pathDataNodeArr2 = node;
            Path path3 = path2;
            float[] current = new float[6];
            char previousCommand = 'm';
            for (int i = 0; i < node.length; i++) {
                addCommand(path2, current, previousCommand, node[i].type, node[i].params);
                previousCommand = node[i].type;
            }
        }

        public void interpolatePathDataNode(PathDataNode pathDataNode, PathDataNode pathDataNode2, float f) {
            PathDataNode nodeFrom = pathDataNode;
            PathDataNode nodeTo = pathDataNode2;
            float fraction = f;
            PathDataNode pathDataNode3 = nodeFrom;
            PathDataNode pathDataNode4 = nodeTo;
            float f2 = fraction;
            for (int i = 0; i < nodeFrom.params.length; i++) {
                this.params[i] = (nodeFrom.params[i] * (1.0f - fraction)) + (nodeTo.params[i] * fraction);
            }
        }

        private static void addCommand(Path path, float[] fArr, char c, char c2, float[] fArr2) {
            boolean z;
            boolean z2;
            Path path2 = path;
            float[] current = fArr;
            float[] val = fArr2;
            Path path3 = path2;
            Object obj = current;
            char previousCmd = c;
            char cmd = c2;
            Object obj2 = val;
            int incr = 2;
            float currentX = current[0];
            float currentY = current[1];
            float ctrlPointX = current[2];
            float ctrlPointY = current[3];
            float currentSegmentStartX = current[4];
            float currentSegmentStartY = current[5];
            switch (cmd) {
                case 'A':
                case 'a':
                    incr = 7;
                    break;
                case 'C':
                case 'c':
                    incr = 6;
                    break;
                case 'H':
                case 'V':
                case 'h':
                case 'v':
                    incr = 1;
                    break;
                case 'L':
                case 'M':
                case 'T':
                case 'l':
                case 'm':
                case 't':
                    incr = 2;
                    break;
                case 'Q':
                case 'S':
                case 'q':
                case 's':
                    incr = 4;
                    break;
                case 'Z':
                case 'z':
                    path2.close();
                    currentX = currentSegmentStartX;
                    currentY = currentSegmentStartY;
                    ctrlPointX = currentSegmentStartX;
                    ctrlPointY = currentSegmentStartY;
                    path2.moveTo(currentSegmentStartX, currentSegmentStartY);
                    break;
            }
            for (int k = 0; k < val.length; k += incr) {
                switch (cmd) {
                    case 'A':
                        float f = currentX;
                        float f2 = currentY;
                        float f3 = val[k + 5];
                        float f4 = val[k + 6];
                        float f5 = val[k + 0];
                        float f6 = val[k + 1];
                        float f7 = val[k + 2];
                        boolean z3 = val[k + 3] != 0.0f;
                        if (val[k + 4] != 0.0f) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        drawArc(path2, f, f2, f3, f4, f5, f6, f7, z3, z2);
                        currentX = val[k + 5];
                        currentY = val[k + 6];
                        ctrlPointX = currentX;
                        ctrlPointY = currentY;
                        break;
                    case 'C':
                        path2.cubicTo(val[k + 0], val[k + 1], val[k + 2], val[k + 3], val[k + 4], val[k + 5]);
                        currentX = val[k + 4];
                        currentY = val[k + 5];
                        ctrlPointX = val[k + 2];
                        ctrlPointY = val[k + 3];
                        break;
                    case 'H':
                        path2.lineTo(val[k + 0], currentY);
                        currentX = val[k + 0];
                        break;
                    case 'L':
                        path2.lineTo(val[k + 0], val[k + 1]);
                        currentX = val[k + 0];
                        currentY = val[k + 1];
                        break;
                    case 'M':
                        currentX = val[k + 0];
                        currentY = val[k + 1];
                        if (k > 0) {
                            path2.lineTo(val[k + 0], val[k + 1]);
                            break;
                        } else {
                            path2.moveTo(val[k + 0], val[k + 1]);
                            currentSegmentStartX = currentX;
                            currentSegmentStartY = currentY;
                            break;
                        }
                    case 'Q':
                        path2.quadTo(val[k + 0], val[k + 1], val[k + 2], val[k + 3]);
                        ctrlPointX = val[k + 0];
                        ctrlPointY = val[k + 1];
                        currentX = val[k + 2];
                        currentY = val[k + 3];
                        break;
                    case 'S':
                        float reflectiveCtrlPointX = currentX;
                        float reflectiveCtrlPointY = currentY;
                        if (previousCmd == 'c' || previousCmd == 's' || previousCmd == 'C' || previousCmd == 'S') {
                            reflectiveCtrlPointX = (2.0f * currentX) - ctrlPointX;
                            reflectiveCtrlPointY = (2.0f * currentY) - ctrlPointY;
                        }
                        path2.cubicTo(reflectiveCtrlPointX, reflectiveCtrlPointY, val[k + 0], val[k + 1], val[k + 2], val[k + 3]);
                        ctrlPointX = val[k + 0];
                        ctrlPointY = val[k + 1];
                        currentX = val[k + 2];
                        currentY = val[k + 3];
                        break;
                    case 'T':
                        float reflectiveCtrlPointX2 = currentX;
                        float reflectiveCtrlPointY2 = currentY;
                        if (previousCmd == 'q' || previousCmd == 't' || previousCmd == 'Q' || previousCmd == 'T') {
                            reflectiveCtrlPointX2 = (2.0f * currentX) - ctrlPointX;
                            reflectiveCtrlPointY2 = (2.0f * currentY) - ctrlPointY;
                        }
                        path2.quadTo(reflectiveCtrlPointX2, reflectiveCtrlPointY2, val[k + 0], val[k + 1]);
                        ctrlPointX = reflectiveCtrlPointX2;
                        ctrlPointY = reflectiveCtrlPointY2;
                        currentX = val[k + 0];
                        currentY = val[k + 1];
                        break;
                    case 'V':
                        path2.lineTo(currentX, val[k + 0]);
                        currentY = val[k + 0];
                        break;
                    case 'a':
                        float f8 = currentX;
                        float f9 = currentY;
                        float f10 = val[k + 5] + currentX;
                        float f11 = val[k + 6] + currentY;
                        float f12 = val[k + 0];
                        float f13 = val[k + 1];
                        float f14 = val[k + 2];
                        boolean z4 = val[k + 3] != 0.0f;
                        if (val[k + 4] != 0.0f) {
                            z = true;
                        } else {
                            z = false;
                        }
                        drawArc(path2, f8, f9, f10, f11, f12, f13, f14, z4, z);
                        currentX += val[k + 5];
                        currentY += val[k + 6];
                        ctrlPointX = currentX;
                        ctrlPointY = currentY;
                        break;
                    case 'c':
                        path2.rCubicTo(val[k + 0], val[k + 1], val[k + 2], val[k + 3], val[k + 4], val[k + 5]);
                        ctrlPointX = currentX + val[k + 2];
                        ctrlPointY = currentY + val[k + 3];
                        currentX += val[k + 4];
                        currentY += val[k + 5];
                        break;
                    case 'h':
                        path2.rLineTo(val[k + 0], 0.0f);
                        currentX += val[k + 0];
                        break;
                    case 'l':
                        path2.rLineTo(val[k + 0], val[k + 1]);
                        currentX += val[k + 0];
                        currentY += val[k + 1];
                        break;
                    case 'm':
                        currentX += val[k + 0];
                        currentY += val[k + 1];
                        if (k > 0) {
                            path2.rLineTo(val[k + 0], val[k + 1]);
                            break;
                        } else {
                            path2.rMoveTo(val[k + 0], val[k + 1]);
                            currentSegmentStartX = currentX;
                            currentSegmentStartY = currentY;
                            break;
                        }
                    case 'q':
                        path2.rQuadTo(val[k + 0], val[k + 1], val[k + 2], val[k + 3]);
                        ctrlPointX = currentX + val[k + 0];
                        ctrlPointY = currentY + val[k + 1];
                        currentX += val[k + 2];
                        currentY += val[k + 3];
                        break;
                    case 's':
                        float reflectiveCtrlPointX3 = 0.0f;
                        float reflectiveCtrlPointY3 = 0.0f;
                        if (previousCmd == 'c' || previousCmd == 's' || previousCmd == 'C' || previousCmd == 'S') {
                            reflectiveCtrlPointX3 = currentX - ctrlPointX;
                            reflectiveCtrlPointY3 = currentY - ctrlPointY;
                        }
                        path2.rCubicTo(reflectiveCtrlPointX3, reflectiveCtrlPointY3, val[k + 0], val[k + 1], val[k + 2], val[k + 3]);
                        ctrlPointX = currentX + val[k + 0];
                        ctrlPointY = currentY + val[k + 1];
                        currentX += val[k + 2];
                        currentY += val[k + 3];
                        break;
                    case 't':
                        float reflectiveCtrlPointX4 = 0;
                        float reflectiveCtrlPointY4 = 0.0f;
                        if (previousCmd == 'q' || previousCmd == 't' || previousCmd == 'Q' || previousCmd == 'T') {
                            reflectiveCtrlPointX4 = currentX - ctrlPointX;
                            reflectiveCtrlPointY4 = currentY - ctrlPointY;
                        }
                        path2.rQuadTo(reflectiveCtrlPointX4, reflectiveCtrlPointY4, val[k + 0], val[k + 1]);
                        ctrlPointX = currentX + reflectiveCtrlPointX4;
                        ctrlPointY = currentY + reflectiveCtrlPointY4;
                        currentX += val[k + 0];
                        currentY += val[k + 1];
                        break;
                    case 'v':
                        path2.rLineTo(0.0f, val[k + 0]);
                        currentY += val[k + 0];
                        break;
                }
                previousCmd = cmd;
            }
            current[0] = currentX;
            current[1] = currentY;
            current[2] = ctrlPointX;
            current[3] = ctrlPointY;
            current[4] = currentSegmentStartX;
            current[5] = currentSegmentStartY;
        }

        private static void drawArc(Path path, float f, float f2, float f3, float f4, float f5, float f6, float f7, boolean z, boolean z2) {
            double cx;
            double cy;
            Path p = path;
            float x0 = f;
            float y0 = f2;
            float x1 = f3;
            float y1 = f4;
            float a = f5;
            float b = f6;
            float theta = f7;
            Path path2 = p;
            float f8 = x0;
            float f9 = y0;
            float f10 = x1;
            float f11 = y1;
            float f12 = a;
            float f13 = b;
            float f14 = theta;
            boolean isMoreThanHalf = z;
            boolean isPositiveArc = z2;
            double radians = Math.toRadians((double) theta);
            double thetaD = radians;
            double cosTheta = Math.cos(radians);
            double sinTheta = Math.sin(thetaD);
            double x0p = ((((double) x0) * cosTheta) + (((double) y0) * sinTheta)) / ((double) a);
            double y0p = ((((double) (-x0)) * sinTheta) + (((double) y0) * cosTheta)) / ((double) b);
            double x1p = ((((double) x1) * cosTheta) + (((double) y1) * sinTheta)) / ((double) a);
            double y1p = ((((double) (-x1)) * sinTheta) + (((double) y1) * cosTheta)) / ((double) b);
            double dx = x0p - x1p;
            double dy = y0p - y1p;
            double xm = (x0p + x1p) / 2.0d;
            double ym = (y0p + y1p) / 2.0d;
            double d = (dx * dx) + (dy * dy);
            double dsq = d;
            if (d == 0.0d) {
                int w = Log.w(PathParser.LOGTAG, " Points are coincident");
                return;
            }
            double d2 = (1.0d / dsq) - 0.25d;
            double disc = d2;
            if (d2 < 0.0d) {
                int w2 = Log.w(PathParser.LOGTAG, "Points are too far apart " + dsq);
                float sqrt = (float) (Math.sqrt(dsq) / 1.99999d);
                float f15 = sqrt;
                drawArc(p, x0, y0, x1, y1, a * sqrt, b * sqrt, theta, isMoreThanHalf, isPositiveArc);
                return;
            }
            double sqrt2 = Math.sqrt(disc);
            double sdx = sqrt2 * dx;
            double sdy = sqrt2 * dy;
            if (isMoreThanHalf != isPositiveArc) {
                cx = xm + sdy;
                cy = ym - sdx;
            } else {
                cx = xm - sdy;
                cy = ym + sdx;
            }
            double eta0 = Math.atan2(y0p - cy, x0p - cx);
            double atan2 = Math.atan2(y1p - cy, x1p - cx);
            double d3 = atan2;
            double d4 = atan2 - eta0;
            double sweep = d4;
            if (isPositiveArc != (d4 >= 0.0d)) {
                if (d4 > 0.0d) {
                    sweep -= 6.283185307179586d;
                } else {
                    sweep += 6.283185307179586d;
                }
            }
            double cx2 = cx * ((double) a);
            double cy2 = cy * ((double) b);
            double tcx = cx2;
            double cx3 = (cx2 * cosTheta) - (cy2 * sinTheta);
            double d5 = (tcx * sinTheta) + (cy2 * cosTheta);
            double cy3 = d5;
            arcToBezier(p, cx3, d5, (double) a, (double) b, (double) x0, (double) y0, thetaD, eta0, sweep);
        }

        private static void arcToBezier(Path path, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9) {
            Path p = path;
            double cx = d;
            double cy = d2;
            double a = d3;
            double b = d4;
            double theta = d7;
            double start = d8;
            double sweep = d9;
            Path path2 = p;
            double d10 = cx;
            double d11 = cy;
            double d12 = a;
            double d13 = b;
            double e1x = d5;
            double e1y = d6;
            double d14 = theta;
            double d15 = start;
            double d16 = sweep;
            int ceil = (int) Math.ceil(Math.abs((sweep * 4.0d) / 3.141592653589793d));
            int numSegments = ceil;
            double eta1 = start;
            double cosTheta = Math.cos(theta);
            double sinTheta = Math.sin(theta);
            double cosEta1 = Math.cos(start);
            double sinEta1 = Math.sin(start);
            double ep1x = (((-a) * cosTheta) * sinEta1) - ((b * sinTheta) * cosEta1);
            double ep1y = ((-a) * sinTheta * sinEta1) + (b * cosTheta * cosEta1);
            double anglePerSegment = sweep / ((double) ceil);
            for (int i = 0; i < numSegments; i++) {
                double d17 = eta1 + anglePerSegment;
                double eta2 = d17;
                double sinEta2 = Math.sin(d17);
                double cosEta2 = Math.cos(eta2);
                double e2x = (cx + ((a * cosTheta) * cosEta2)) - ((b * sinTheta) * sinEta2);
                double e2y = cy + (a * sinTheta * cosEta2) + (b * cosTheta * sinEta2);
                double ep2x = (((-a) * cosTheta) * sinEta2) - ((b * sinTheta) * cosEta2);
                double ep2y = ((-a) * sinTheta * sinEta2) + (b * cosTheta * cosEta2);
                double tanDiff2 = Math.tan((eta2 - eta1) / 2.0d);
                double alpha = (Math.sin(eta2 - eta1) * (Math.sqrt(4.0d + ((3.0d * tanDiff2) * tanDiff2)) - 1.0d)) / 3.0d;
                float f = ((float) e2y) - ((float) e1y);
                float f2 = f;
                p.rCubicTo(((float) (e1x + (alpha * ep1x))) - ((float) e1x), ((float) (e1y + (alpha * ep1y))) - ((float) e1y), ((float) (e2x - (alpha * ep2x))) - ((float) e1x), ((float) (e2y - (alpha * ep2y))) - ((float) e1y), ((float) e2x) - ((float) e1x), f);
                eta1 = eta2;
                e1x = e2x;
                e1y = e2y;
                ep1x = ep2x;
                ep1y = ep2y;
            }
        }
    }

    PathParser() {
    }

    static float[] copyOfRange(float[] fArr, int i, int i2) {
        float[] original = fArr;
        int start = i;
        int end = i2;
        float[] fArr2 = original;
        int i3 = start;
        int i4 = end;
        if (start <= end) {
            int originalLength = original.length;
            if (start >= 0 && start <= originalLength) {
                int i5 = end - start;
                float[] result = new float[i5];
                System.arraycopy(original, start, result, 0, Math.min(i5, originalLength - start));
                return result;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
        throw new IllegalArgumentException();
    }

    public static Path createPathFromPathData(String str) {
        String pathData = str;
        String str2 = pathData;
        Path path = new Path();
        PathDataNode[] createNodesFromPathData = createNodesFromPathData(pathData);
        PathDataNode[] nodes = createNodesFromPathData;
        if (createNodesFromPathData == null) {
            return null;
        }
        try {
            PathDataNode.nodesToPath(nodes, path);
            return path;
        } catch (RuntimeException e) {
            RuntimeException runtimeException = e;
            throw new RuntimeException("Error in parsing " + pathData, e);
        }
    }

    public static PathDataNode[] createNodesFromPathData(String str) {
        String pathData = str;
        String str2 = pathData;
        if (pathData == null) {
            return null;
        }
        int start = 0;
        int end = 1;
        ArrayList arrayList = new ArrayList();
        while (end < pathData.length()) {
            int nextStart = nextStart(pathData, end);
            int end2 = nextStart;
            String trim = pathData.substring(start, nextStart).trim();
            String s = trim;
            if (trim.length() > 0) {
                addNode(arrayList, s.charAt(0), getFloats(s));
            }
            start = end2;
            end = end2 + 1;
        }
        if (end - start == 1 && start < pathData.length()) {
            addNode(arrayList, pathData.charAt(start), new float[0]);
        }
        return (PathDataNode[]) arrayList.toArray(new PathDataNode[arrayList.size()]);
    }

    public static PathDataNode[] deepCopyNodes(PathDataNode[] pathDataNodeArr) {
        PathDataNode[] source = pathDataNodeArr;
        PathDataNode[] pathDataNodeArr2 = source;
        if (source == null) {
            return null;
        }
        PathDataNode[] copy = new PathDataNode[source.length];
        for (int i = 0; i < source.length; i++) {
            copy[i] = new PathDataNode(source[i]);
        }
        return copy;
    }

    public static boolean canMorph(PathDataNode[] pathDataNodeArr, PathDataNode[] pathDataNodeArr2) {
        PathDataNode[] nodesFrom = pathDataNodeArr;
        PathDataNode[] nodesTo = pathDataNodeArr2;
        PathDataNode[] pathDataNodeArr3 = nodesFrom;
        PathDataNode[] pathDataNodeArr4 = nodesTo;
        if (nodesFrom == null || nodesTo == null) {
            return false;
        }
        if (nodesFrom.length != nodesTo.length) {
            return false;
        }
        for (int i = 0; i < nodesFrom.length; i++) {
            if (nodesFrom[i].type != nodesTo[i].type || nodesFrom[i].params.length != nodesTo[i].params.length) {
                return false;
            }
        }
        return true;
    }

    public static void updateNodes(PathDataNode[] pathDataNodeArr, PathDataNode[] pathDataNodeArr2) {
        PathDataNode[] target = pathDataNodeArr;
        PathDataNode[] source = pathDataNodeArr2;
        PathDataNode[] pathDataNodeArr3 = target;
        PathDataNode[] pathDataNodeArr4 = source;
        for (int i = 0; i < source.length; i++) {
            target[i].type = (char) source[i].type;
            for (int j = 0; j < source[i].params.length; j++) {
                target[i].params[j] = source[i].params[j];
            }
        }
    }

    private static int nextStart(String str, int i) {
        String s = str;
        String str2 = s;
        int end = i;
        while (end < s.length()) {
            char charAt = s.charAt(end);
            char c = charAt;
            if (((charAt - 'A') * (c - 'Z') <= 0 || (c - 'a') * (c - 'z') <= 0) && c != 'e' && c != 'E') {
                return end;
            }
            end++;
        }
        return end;
    }

    private static void addNode(ArrayList<PathDataNode> arrayList, char c, float[] fArr) {
        ArrayList<PathDataNode> list = arrayList;
        float[] val = fArr;
        ArrayList<PathDataNode> arrayList2 = list;
        float[] fArr2 = val;
        boolean add = list.add(new PathDataNode(c, val));
    }

    private static float[] getFloats(String str) {
        String s = str;
        String str2 = s;
        if ((s.charAt(0) == 'z') || (s.charAt(0) == 'Z')) {
            return new float[0];
        }
        try {
            float[] results = new float[s.length()];
            int count = 0;
            int startPosition = 1;
            ExtractFloatResult result = new ExtractFloatResult();
            int totalLength = s.length();
            while (startPosition < totalLength) {
                extract(s, startPosition, result);
                int endPosition = result.mEndPosition;
                if (startPosition < endPosition) {
                    int i = count;
                    count++;
                    results[i] = Float.parseFloat(s.substring(startPosition, endPosition));
                }
                if (!result.mEndWithNegOrDot) {
                    startPosition = endPosition + 1;
                } else {
                    startPosition = endPosition;
                }
            }
            return copyOfRange(results, 0, count);
        } catch (NumberFormatException e) {
            NumberFormatException numberFormatException = e;
            throw new RuntimeException("error in parsing \"" + s + "\"", e);
        }
    }

    private static void extract(String str, int i, ExtractFloatResult extractFloatResult) {
        String s = str;
        int start = i;
        ExtractFloatResult result = extractFloatResult;
        String str2 = s;
        int i2 = start;
        ExtractFloatResult extractFloatResult2 = result;
        int currentIndex = start;
        boolean foundSeparator = false;
        result.mEndWithNegOrDot = false;
        boolean secondDot = false;
        boolean isExponential = false;
        while (currentIndex < s.length()) {
            boolean isPrevExponential = isExponential;
            isExponential = false;
            char charAt = s.charAt(currentIndex);
            char c = charAt;
            switch (charAt) {
                case ' ':
                case ',':
                    foundSeparator = true;
                    break;
                case '-':
                    if (currentIndex != start && !isPrevExponential) {
                        foundSeparator = true;
                        result.mEndWithNegOrDot = true;
                        break;
                    }
                case '.':
                    if (!secondDot) {
                        secondDot = true;
                        break;
                    } else {
                        foundSeparator = true;
                        result.mEndWithNegOrDot = true;
                        break;
                    }
                case 'E':
                case 'e':
                    isExponential = true;
                    break;
            }
            if (!foundSeparator) {
                currentIndex++;
            } else {
                result.mEndPosition = currentIndex;
            }
        }
        result.mEndPosition = currentIndex;
    }
}
