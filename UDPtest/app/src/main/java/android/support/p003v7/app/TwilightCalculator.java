package android.support.p003v7.app;

/* renamed from: android.support.v7.app.TwilightCalculator */
class TwilightCalculator {
    private static final float ALTIDUTE_CORRECTION_CIVIL_TWILIGHT = -0.10471976f;

    /* renamed from: C1 */
    private static final float f19C1 = 0.0334196f;

    /* renamed from: C2 */
    private static final float f20C2 = 3.49066E-4f;

    /* renamed from: C3 */
    private static final float f21C3 = 5.236E-6f;
    public static final int DAY = 0;
    private static final float DEGREES_TO_RADIANS = 0.017453292f;

    /* renamed from: J0 */
    private static final float f22J0 = 9.0E-4f;
    public static final int NIGHT = 1;
    private static final float OBLIQUITY = 0.4092797f;
    private static final long UTC_2000 = 946728000000L;
    private static TwilightCalculator sInstance;
    public int state;
    public long sunrise;
    public long sunset;

    TwilightCalculator() {
    }

    static TwilightCalculator getInstance() {
        if (sInstance == null) {
            sInstance = new TwilightCalculator();
        }
        return sInstance;
    }

    public void calculateTwilight(long j, double d, double d2) {
        long time = j;
        double latitude = d;
        double longitude = d2;
        long j2 = time;
        double d3 = latitude;
        double d4 = longitude;
        float daysSince2000 = ((float) (time - UTC_2000)) / 8.64E7f;
        float f = 6.24006f + (daysSince2000 * 0.01720197f);
        float meanAnomaly = f;
        double sin = ((double) f) + (0.03341960161924362d * Math.sin((double) meanAnomaly)) + (3.4906598739326E-4d * Math.sin((double) (2.0f * meanAnomaly))) + (5.236000106378924E-6d * Math.sin((double) (3.0f * meanAnomaly)));
        double d5 = sin;
        double solarLng = sin + 1.796593063d + 3.141592653589793d;
        double arcLongitude = (-longitude) / 360.0d;
        float round = (float) Math.round(((double) (daysSince2000 - f22J0)) - arcLongitude);
        float f2 = round;
        double solarTransitJ2000 = ((double) (round + f22J0)) + arcLongitude + (0.0053d * Math.sin((double) meanAnomaly)) + (-0.0069d * Math.sin(2.0d * solarLng));
        double solarDec = Math.asin(Math.sin(solarLng) * Math.sin(0.4092797040939331d));
        double latRad = latitude * 0.01745329238474369d;
        double sin2 = (Math.sin(-0.10471975803375244d) - (Math.sin(latRad) * Math.sin(solarDec))) / (Math.cos(latRad) * Math.cos(solarDec));
        double cosHourAngle = sin2;
        if (sin2 >= 1.0d) {
            this.state = 1;
            this.sunset = -1;
            this.sunrise = -1;
        } else if (cosHourAngle <= -1.0d) {
            this.state = 0;
            this.sunset = -1;
            this.sunrise = -1;
        } else {
            float acos = (float) (Math.acos(cosHourAngle) / 6.283185307179586d);
            float f3 = acos;
            this.sunset = Math.round((solarTransitJ2000 + ((double) acos)) * 8.64E7d) + UTC_2000;
            this.sunrise = Math.round((solarTransitJ2000 - ((double) acos)) * 8.64E7d) + UTC_2000;
            if (!(this.sunrise >= time)) {
                if (!(this.sunset <= time)) {
                    this.state = 0;
                }
            }
            this.state = 1;
        }
    }
}
