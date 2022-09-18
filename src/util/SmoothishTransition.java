package util;
import javafx.animation.Transition;
import javafx.util.Duration;

public abstract class SmoothishTransition extends Transition {
    private final double mod;
    private final double delta;

    private final static int TRANSITION_DURATION = 200;

    public SmoothishTransition(SmoothishTransition old, double delta) {
        setCycleDuration(Duration.millis(TRANSITION_DURATION));
        setCycleCount(0);
        // if the last transition was moving inthe same direction, and is still playing
        // then increment the modifer. This will boost the distance, thus looking faster
        // and seemingly consecutive.
        if (old != null && sameSign(delta, old.delta) && playing(old)) {
            mod = old.getMod() + 1;
        } else {
            mod = 1;
        }
        this.delta = delta;
    }

    public double getMod() {
        return mod;
    }

    @Override
    public void play() {
        super.play();
        // Even with a linear interpolation, startup is visibly slower than the middle.
        // So skip a small bit of the animation to keep up with the speed of prior
        // animation. The value of 10 works and isn't noticeable unless you really pay
        // close attention. This works best on linear but also is decent for others.
        if (getMod() > 1) {
            jumpTo(getCycleDuration().divide(10));
        }
    }

    private static boolean playing(Transition t) {
        return t.getStatus() == Status.RUNNING;
    }

    private static boolean sameSign(double d1, double d2) {
        return (d1 > 0 && d2 > 0) || (d1 < 0 && d2 < 0);
    }
}