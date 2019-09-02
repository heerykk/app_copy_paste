package android.support.p000v4.hardware.fingerprint;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p000v4.hardware.fingerprint.FingerprintManagerCompatApi23.AuthenticationResultInternal;
import android.support.p000v4.p002os.CancellationSignal;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.Mac;

/* renamed from: android.support.v4.hardware.fingerprint.FingerprintManagerCompat */
public final class FingerprintManagerCompat {
    static final FingerprintManagerCompatImpl IMPL;
    private Context mContext;

    /* renamed from: android.support.v4.hardware.fingerprint.FingerprintManagerCompat$Api23FingerprintManagerCompatImpl */
    private static class Api23FingerprintManagerCompatImpl implements FingerprintManagerCompatImpl {
        public Api23FingerprintManagerCompatImpl() {
        }

        public boolean hasEnrolledFingerprints(Context context) {
            Context context2 = context;
            Context context3 = context2;
            return FingerprintManagerCompatApi23.hasEnrolledFingerprints(context2);
        }

        public boolean isHardwareDetected(Context context) {
            Context context2 = context;
            Context context3 = context2;
            return FingerprintManagerCompatApi23.isHardwareDetected(context2);
        }

        public void authenticate(Context context, CryptoObject cryptoObject, int i, CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback, Handler handler) {
            Context context2 = context;
            CryptoObject crypto = cryptoObject;
            int flags = i;
            CancellationSignal cancel = cancellationSignal;
            AuthenticationCallback callback = authenticationCallback;
            Handler handler2 = handler;
            Context context3 = context2;
            CryptoObject cryptoObject2 = crypto;
            int i2 = flags;
            CancellationSignal cancellationSignal2 = cancel;
            AuthenticationCallback authenticationCallback2 = callback;
            Handler handler3 = handler2;
            FingerprintManagerCompatApi23.authenticate(context2, wrapCryptoObject(crypto), flags, cancel == null ? null : cancel.getCancellationSignalObject(), wrapCallback(callback), handler2);
        }

        private static android.support.p000v4.hardware.fingerprint.FingerprintManagerCompatApi23.CryptoObject wrapCryptoObject(CryptoObject cryptoObject) {
            CryptoObject cryptoObject2 = cryptoObject;
            CryptoObject cryptoObject3 = cryptoObject2;
            if (cryptoObject2 == null) {
                return null;
            }
            if (cryptoObject2.getCipher() != null) {
                return new android.support.p000v4.hardware.fingerprint.FingerprintManagerCompatApi23.CryptoObject(cryptoObject2.getCipher());
            }
            if (cryptoObject2.getSignature() != null) {
                return new android.support.p000v4.hardware.fingerprint.FingerprintManagerCompatApi23.CryptoObject(cryptoObject2.getSignature());
            }
            if (cryptoObject2.getMac() == null) {
                return null;
            }
            return new android.support.p000v4.hardware.fingerprint.FingerprintManagerCompatApi23.CryptoObject(cryptoObject2.getMac());
        }

        static CryptoObject unwrapCryptoObject(android.support.p000v4.hardware.fingerprint.FingerprintManagerCompatApi23.CryptoObject cryptoObject) {
            android.support.p000v4.hardware.fingerprint.FingerprintManagerCompatApi23.CryptoObject cryptoObject2 = cryptoObject;
            android.support.p000v4.hardware.fingerprint.FingerprintManagerCompatApi23.CryptoObject cryptoObject3 = cryptoObject2;
            if (cryptoObject2 == null) {
                return null;
            }
            if (cryptoObject2.getCipher() != null) {
                return new CryptoObject(cryptoObject2.getCipher());
            }
            if (cryptoObject2.getSignature() != null) {
                return new CryptoObject(cryptoObject2.getSignature());
            }
            if (cryptoObject2.getMac() == null) {
                return null;
            }
            return new CryptoObject(cryptoObject2.getMac());
        }

        private static android.support.p000v4.hardware.fingerprint.FingerprintManagerCompatApi23.AuthenticationCallback wrapCallback(AuthenticationCallback authenticationCallback) {
            AuthenticationCallback callback = authenticationCallback;
            AuthenticationCallback authenticationCallback2 = callback;
            final AuthenticationCallback authenticationCallback3 = callback;
            return new android.support.p000v4.hardware.fingerprint.FingerprintManagerCompatApi23.AuthenticationCallback() {
                public void onAuthenticationError(int i, CharSequence charSequence) {
                    int errMsgId = i;
                    CharSequence errString = charSequence;
                    int i2 = errMsgId;
                    CharSequence charSequence2 = errString;
                    authenticationCallback3.onAuthenticationError(errMsgId, errString);
                }

                public void onAuthenticationHelp(int i, CharSequence charSequence) {
                    int helpMsgId = i;
                    CharSequence helpString = charSequence;
                    int i2 = helpMsgId;
                    CharSequence charSequence2 = helpString;
                    authenticationCallback3.onAuthenticationHelp(helpMsgId, helpString);
                }

                public void onAuthenticationSucceeded(AuthenticationResultInternal authenticationResultInternal) {
                    AuthenticationResultInternal result = authenticationResultInternal;
                    AuthenticationResultInternal authenticationResultInternal2 = result;
                    authenticationCallback3.onAuthenticationSucceeded(new AuthenticationResult(Api23FingerprintManagerCompatImpl.unwrapCryptoObject(result.getCryptoObject())));
                }

                public void onAuthenticationFailed() {
                    authenticationCallback3.onAuthenticationFailed();
                }
            };
        }
    }

    /* renamed from: android.support.v4.hardware.fingerprint.FingerprintManagerCompat$AuthenticationCallback */
    public static abstract class AuthenticationCallback {
        public AuthenticationCallback() {
        }

        public void onAuthenticationError(int i, CharSequence charSequence) {
            int i2 = i;
            CharSequence charSequence2 = charSequence;
        }

        public void onAuthenticationHelp(int i, CharSequence charSequence) {
            int i2 = i;
            CharSequence charSequence2 = charSequence;
        }

        public void onAuthenticationSucceeded(AuthenticationResult authenticationResult) {
            AuthenticationResult authenticationResult2 = authenticationResult;
        }

        public void onAuthenticationFailed() {
        }
    }

    /* renamed from: android.support.v4.hardware.fingerprint.FingerprintManagerCompat$AuthenticationResult */
    public static final class AuthenticationResult {
        private CryptoObject mCryptoObject;

        public AuthenticationResult(CryptoObject cryptoObject) {
            CryptoObject crypto = cryptoObject;
            CryptoObject cryptoObject2 = crypto;
            this.mCryptoObject = crypto;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }
    }

    /* renamed from: android.support.v4.hardware.fingerprint.FingerprintManagerCompat$CryptoObject */
    public static class CryptoObject {
        private final Cipher mCipher;
        private final Mac mMac;
        private final Signature mSignature;

        public CryptoObject(Signature signature) {
            Signature signature2 = signature;
            Signature signature3 = signature2;
            this.mSignature = signature2;
            this.mCipher = null;
            this.mMac = null;
        }

        public CryptoObject(Cipher cipher) {
            Cipher cipher2 = cipher;
            Cipher cipher3 = cipher2;
            this.mCipher = cipher2;
            this.mSignature = null;
            this.mMac = null;
        }

        public CryptoObject(Mac mac) {
            Mac mac2 = mac;
            Mac mac3 = mac2;
            this.mMac = mac2;
            this.mCipher = null;
            this.mSignature = null;
        }

        public Signature getSignature() {
            return this.mSignature;
        }

        public Cipher getCipher() {
            return this.mCipher;
        }

        public Mac getMac() {
            return this.mMac;
        }
    }

    /* renamed from: android.support.v4.hardware.fingerprint.FingerprintManagerCompat$FingerprintManagerCompatImpl */
    private interface FingerprintManagerCompatImpl {
        void authenticate(Context context, CryptoObject cryptoObject, int i, CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback, Handler handler);

        boolean hasEnrolledFingerprints(Context context);

        boolean isHardwareDetected(Context context);
    }

    /* renamed from: android.support.v4.hardware.fingerprint.FingerprintManagerCompat$LegacyFingerprintManagerCompatImpl */
    private static class LegacyFingerprintManagerCompatImpl implements FingerprintManagerCompatImpl {
        public LegacyFingerprintManagerCompatImpl() {
        }

        public boolean hasEnrolledFingerprints(Context context) {
            Context context2 = context;
            return false;
        }

        public boolean isHardwareDetected(Context context) {
            Context context2 = context;
            return false;
        }

        public void authenticate(Context context, CryptoObject cryptoObject, int i, CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback, Handler handler) {
            Context context2 = context;
            CryptoObject cryptoObject2 = cryptoObject;
            int i2 = i;
            CancellationSignal cancellationSignal2 = cancellationSignal;
            AuthenticationCallback authenticationCallback2 = authenticationCallback;
            Handler handler2 = handler;
        }
    }

    public static FingerprintManagerCompat from(Context context) {
        Context context2 = context;
        Context context3 = context2;
        return new FingerprintManagerCompat(context2);
    }

    private FingerprintManagerCompat(Context context) {
        Context context2 = context;
        Context context3 = context2;
        this.mContext = context2;
    }

    static {
        int i = VERSION.SDK_INT;
        int i2 = i;
        if (i < 23) {
            IMPL = new LegacyFingerprintManagerCompatImpl();
        } else {
            IMPL = new Api23FingerprintManagerCompatImpl();
        }
    }

    public boolean hasEnrolledFingerprints() {
        return IMPL.hasEnrolledFingerprints(this.mContext);
    }

    public boolean isHardwareDetected() {
        return IMPL.isHardwareDetected(this.mContext);
    }

    public void authenticate(@Nullable CryptoObject cryptoObject, int i, @Nullable CancellationSignal cancellationSignal, @NonNull AuthenticationCallback authenticationCallback, @Nullable Handler handler) {
        CryptoObject crypto = cryptoObject;
        int flags = i;
        CancellationSignal cancel = cancellationSignal;
        AuthenticationCallback callback = authenticationCallback;
        Handler handler2 = handler;
        CryptoObject cryptoObject2 = crypto;
        int i2 = flags;
        CancellationSignal cancellationSignal2 = cancel;
        AuthenticationCallback authenticationCallback2 = callback;
        Handler handler3 = handler2;
        IMPL.authenticate(this.mContext, crypto, flags, cancel, callback, handler2);
    }
}
