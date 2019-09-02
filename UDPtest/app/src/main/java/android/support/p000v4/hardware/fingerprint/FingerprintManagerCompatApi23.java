package android.support.p000v4.hardware.fingerprint;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintManager.AuthenticationResult;
import android.os.CancellationSignal;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.Mac;

@RequiresApi(23)
@RestrictTo({Scope.LIBRARY_GROUP})
@TargetApi(23)
/* renamed from: android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23 */
public final class FingerprintManagerCompatApi23 {

    /* renamed from: android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23$AuthenticationCallback */
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

        public void onAuthenticationSucceeded(AuthenticationResultInternal authenticationResultInternal) {
            AuthenticationResultInternal authenticationResultInternal2 = authenticationResultInternal;
        }

        public void onAuthenticationFailed() {
        }
    }

    /* renamed from: android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23$AuthenticationResultInternal */
    public static final class AuthenticationResultInternal {
        private CryptoObject mCryptoObject;

        public AuthenticationResultInternal(CryptoObject cryptoObject) {
            CryptoObject crypto = cryptoObject;
            CryptoObject cryptoObject2 = crypto;
            this.mCryptoObject = crypto;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }
    }

    /* renamed from: android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23$CryptoObject */
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

    public FingerprintManagerCompatApi23() {
    }

    static /* synthetic */ CryptoObject access$000(android.hardware.fingerprint.FingerprintManager.CryptoObject cryptoObject) {
        android.hardware.fingerprint.FingerprintManager.CryptoObject x0 = cryptoObject;
        android.hardware.fingerprint.FingerprintManager.CryptoObject cryptoObject2 = x0;
        return unwrapCryptoObject(x0);
    }

    private static FingerprintManager getFingerprintManagerOrNull(Context context) {
        Context context2 = context;
        Context context3 = context2;
        if (!context2.getPackageManager().hasSystemFeature("android.hardware.fingerprint")) {
            return null;
        }
        return (FingerprintManager) context2.getSystemService(FingerprintManager.class);
    }

    public static boolean hasEnrolledFingerprints(Context context) {
        Context context2 = context;
        Context context3 = context2;
        FingerprintManager fingerprintManagerOrNull = getFingerprintManagerOrNull(context2);
        return fingerprintManagerOrNull != null && fingerprintManagerOrNull.hasEnrolledFingerprints();
    }

    public static boolean isHardwareDetected(Context context) {
        Context context2 = context;
        Context context3 = context2;
        FingerprintManager fingerprintManagerOrNull = getFingerprintManagerOrNull(context2);
        return fingerprintManagerOrNull != null && fingerprintManagerOrNull.isHardwareDetected();
    }

    public static void authenticate(Context context, CryptoObject cryptoObject, int i, Object obj, AuthenticationCallback authenticationCallback, Handler handler) {
        Context context2 = context;
        CryptoObject crypto = cryptoObject;
        int flags = i;
        Object cancel = obj;
        AuthenticationCallback callback = authenticationCallback;
        Handler handler2 = handler;
        Context context3 = context2;
        CryptoObject cryptoObject2 = crypto;
        int i2 = flags;
        Object obj2 = cancel;
        AuthenticationCallback authenticationCallback2 = callback;
        Handler handler3 = handler2;
        FingerprintManager fingerprintManagerOrNull = getFingerprintManagerOrNull(context2);
        FingerprintManager fp = fingerprintManagerOrNull;
        if (fingerprintManagerOrNull != null) {
            fp.authenticate(wrapCryptoObject(crypto), (CancellationSignal) cancel, flags, wrapCallback(callback), handler2);
        }
    }

    private static android.hardware.fingerprint.FingerprintManager.CryptoObject wrapCryptoObject(CryptoObject cryptoObject) {
        CryptoObject cryptoObject2 = cryptoObject;
        CryptoObject cryptoObject3 = cryptoObject2;
        if (cryptoObject2 == null) {
            return null;
        }
        if (cryptoObject2.getCipher() != null) {
            return new android.hardware.fingerprint.FingerprintManager.CryptoObject(cryptoObject2.getCipher());
        }
        if (cryptoObject2.getSignature() != null) {
            return new android.hardware.fingerprint.FingerprintManager.CryptoObject(cryptoObject2.getSignature());
        }
        if (cryptoObject2.getMac() == null) {
            return null;
        }
        return new android.hardware.fingerprint.FingerprintManager.CryptoObject(cryptoObject2.getMac());
    }

    private static CryptoObject unwrapCryptoObject(android.hardware.fingerprint.FingerprintManager.CryptoObject cryptoObject) {
        android.hardware.fingerprint.FingerprintManager.CryptoObject cryptoObject2 = cryptoObject;
        android.hardware.fingerprint.FingerprintManager.CryptoObject cryptoObject3 = cryptoObject2;
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

    private static android.hardware.fingerprint.FingerprintManager.AuthenticationCallback wrapCallback(AuthenticationCallback authenticationCallback) {
        AuthenticationCallback callback = authenticationCallback;
        AuthenticationCallback authenticationCallback2 = callback;
        final AuthenticationCallback authenticationCallback3 = callback;
        return new android.hardware.fingerprint.FingerprintManager.AuthenticationCallback() {
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

            public void onAuthenticationSucceeded(AuthenticationResult authenticationResult) {
                AuthenticationResult result = authenticationResult;
                AuthenticationResult authenticationResult2 = result;
                authenticationCallback3.onAuthenticationSucceeded(new AuthenticationResultInternal(FingerprintManagerCompatApi23.access$000(result.getCryptoObject())));
            }

            public void onAuthenticationFailed() {
                authenticationCallback3.onAuthenticationFailed();
            }
        };
    }
}
