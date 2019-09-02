package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.p000v4.view.ViewCompat;
import android.view.ViewPropertyAnimator;

@TargetApi(14)
@RequiresApi(14)
class FloatingActionButtonIcs extends FloatingActionButtonGingerbread {
    private float mRotation = this.mView.getRotation();

    FloatingActionButtonIcs(VisibilityAwareImageButton visibilityAwareImageButton, ShadowViewDelegate shadowViewDelegate, Creator creator) {
        VisibilityAwareImageButton view = visibilityAwareImageButton;
        ShadowViewDelegate shadowViewDelegate2 = shadowViewDelegate;
        Creator animatorCreator = creator;
        VisibilityAwareImageButton visibilityAwareImageButton2 = view;
        ShadowViewDelegate shadowViewDelegate3 = shadowViewDelegate2;
        Creator creator2 = animatorCreator;
        super(view, shadowViewDelegate2, animatorCreator);
    }

    /* access modifiers changed from: 0000 */
    public boolean requirePreDrawListener() {
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void onPreDraw() {
        float rotation = this.mView.getRotation();
        if (this.mRotation != rotation) {
            this.mRotation = rotation;
            updateFromViewRotation();
        }
    }

    /* access modifiers changed from: 0000 */
    public void hide(@Nullable InternalVisibilityChangedListener internalVisibilityChangedListener, boolean z) {
        InternalVisibilityChangedListener listener = internalVisibilityChangedListener;
        InternalVisibilityChangedListener internalVisibilityChangedListener2 = listener;
        boolean fromUser = z;
        if (!isOrWillBeHidden()) {
            this.mView.animate().cancel();
            if (!shouldAnimateVisibilityChange()) {
                this.mView.internalSetVisibility(!fromUser ? 4 : 8, fromUser);
                if (listener != null) {
                    listener.onHidden();
                }
            } else {
                this.mAnimState = 1;
                ViewPropertyAnimator interpolator = this.mView.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(200).setInterpolator(AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
                final boolean z2 = fromUser;
                final InternalVisibilityChangedListener internalVisibilityChangedListener3 = listener;
                C00391 r0 = new AnimatorListenerAdapter(this) {
                    private boolean mCancelled;
                    final /* synthetic */ FloatingActionButtonIcs this$0;

                    {
                        FloatingActionButtonIcs this$02 = r9;
                        InternalVisibilityChangedListener internalVisibilityChangedListener = r11;
                        FloatingActionButtonIcs floatingActionButtonIcs = this$02;
                        boolean z = r10;
                        this.this$0 = this$02;
                    }

                    public void onAnimationStart(Animator animator) {
                        Animator animator2 = animator;
                        this.this$0.mView.internalSetVisibility(0, z2);
                        this.mCancelled = false;
                    }

                    public void onAnimationCancel(Animator animator) {
                        Animator animator2 = animator;
                        this.mCancelled = true;
                    }

                    public void onAnimationEnd(Animator animator) {
                        Animator animator2 = animator;
                        this.this$0.mAnimState = 0;
                        if (!this.mCancelled) {
                            this.this$0.mView.internalSetVisibility(!z2 ? 4 : 8, z2);
                            if (internalVisibilityChangedListener3 != null) {
                                internalVisibilityChangedListener3.onHidden();
                            }
                        }
                    }
                };
                ViewPropertyAnimator listener2 = interpolator.setListener(r0);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void show(@Nullable InternalVisibilityChangedListener internalVisibilityChangedListener, boolean z) {
        InternalVisibilityChangedListener listener = internalVisibilityChangedListener;
        InternalVisibilityChangedListener internalVisibilityChangedListener2 = listener;
        boolean fromUser = z;
        if (!isOrWillBeShown()) {
            this.mView.animate().cancel();
            if (!shouldAnimateVisibilityChange()) {
                this.mView.internalSetVisibility(0, fromUser);
                this.mView.setAlpha(1.0f);
                this.mView.setScaleY(1.0f);
                this.mView.setScaleX(1.0f);
                if (listener != null) {
                    listener.onShown();
                }
            } else {
                this.mAnimState = 2;
                if (this.mView.getVisibility() != 0) {
                    this.mView.setAlpha(0.0f);
                    this.mView.setScaleY(0.0f);
                    this.mView.setScaleX(0.0f);
                }
                ViewPropertyAnimator interpolator = this.mView.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200).setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
                final boolean z2 = fromUser;
                final InternalVisibilityChangedListener internalVisibilityChangedListener3 = listener;
                C00402 r0 = new AnimatorListenerAdapter(this) {
                    final /* synthetic */ FloatingActionButtonIcs this$0;

                    {
                        FloatingActionButtonIcs this$02 = r9;
                        InternalVisibilityChangedListener internalVisibilityChangedListener = r11;
                        FloatingActionButtonIcs floatingActionButtonIcs = this$02;
                        boolean z = r10;
                        this.this$0 = this$02;
                    }

                    public void onAnimationStart(Animator animator) {
                        Animator animator2 = animator;
                        this.this$0.mView.internalSetVisibility(0, z2);
                    }

                    public void onAnimationEnd(Animator animator) {
                        Animator animator2 = animator;
                        this.this$0.mAnimState = 0;
                        if (internalVisibilityChangedListener3 != null) {
                            internalVisibilityChangedListener3.onShown();
                        }
                    }
                };
                ViewPropertyAnimator listener2 = interpolator.setListener(r0);
            }
        }
    }

    private boolean shouldAnimateVisibilityChange() {
        return ViewCompat.isLaidOut(this.mView) && !this.mView.isInEditMode();
    }

    private void updateFromViewRotation() {
        if (VERSION.SDK_INT == 19) {
            if (this.mRotation % 90.0f != 0.0f) {
                if (this.mView.getLayerType() != 1) {
                    this.mView.setLayerType(1, null);
                }
            } else if (this.mView.getLayerType() != 0) {
                this.mView.setLayerType(0, null);
            }
        }
        if (this.mShadowDrawable != null) {
            this.mShadowDrawable.setRotation(-this.mRotation);
        }
        if (this.mBorderDrawable != null) {
            this.mBorderDrawable.setRotation(-this.mRotation);
        }
    }
}
