package com.amdege.shash.circularrevealanimation;

import android.animation.Animator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton mFloatButton;
    ImageView imageView;
    ImageView revealedImage;
    Animation alphaAnimation;
    float pixelDensity;
    LinearLayout revealedView;
    boolean revealed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pixelDensity = getResources().getDisplayMetrics().density;

        imageView = (ImageView) findViewById(R.id.imageView);
        mFloatButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        revealedView = (LinearLayout) findViewById(R.id.revealedView);
        alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);
        revealedImage = (ImageView) findViewById(R.id.revealedImage);

    }

    public void launchAnimation(View view) {

        int x = imageView.getRight();
        int y = imageView.getBottom();
        x -= 20*pixelDensity;

        int finalRadius = (int) Math.hypot(imageView.getWidth(),imageView.getHeight());

        if (revealed) {

            mFloatButton.setBackgroundResource(R.drawable.ic_launcher_background);

            FrameLayout.LayoutParams parameters = (FrameLayout.LayoutParams)
                    revealedView.getLayoutParams();
            parameters.height = imageView.getHeight();
            revealedView.setLayoutParams(parameters);

            final Animator revealAnim = ViewAnimationUtils.createCircularReveal(revealedView,x,y,0,finalRadius);
            revealAnim.setDuration(700);

            revealAnim.addListener(new Animator.AnimatorListener(){

                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {

                    revealedView.setVisibility(View.VISIBLE);
                    revealedImage.startAnimation(alphaAnimation);

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            revealedView.setVisibility(View.VISIBLE);
            revealAnim.start();

            revealed = false;

        }else{

            mFloatButton.setBackgroundResource(R.color.colorAccent);

            Animator endAnim = ViewAnimationUtils.createCircularReveal(revealedView, x, y, finalRadius, 0);
            endAnim.setDuration(400);

            endAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    revealedView.setVisibility(View.GONE);
                    revealedImage.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });


            endAnim.start();
            revealed = true;

        }
    }
}
