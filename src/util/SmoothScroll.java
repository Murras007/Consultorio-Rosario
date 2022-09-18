package util;
import javafx.animation.Interpolator;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;

public class SmoothScroll {

    private final static double BASE_MODIFIER = 1;

    public SmoothScroll(final ScrollPane scrollPane, final Node node) {
        this(scrollPane, node, 160);
    }
    public SmoothScroll(final ScrollPane scrollPane, final Node node, final double baseChange) {
        node.setOnScroll(new EventHandler<ScrollEvent>() {
            private SmoothishTransition transition;

            @Override
            public void handle(ScrollEvent event) {
                if (scrollPane==null) {
                    return;
                }
                double deltaYOrg = event.getDeltaY();
                if (deltaYOrg==0) {
                    return;
                }
                double percents = calculatePercents(deltaYOrg>=0);

                final double startingVValue = scrollPane.getVvalue();

                smoothTransition(
                        startingVValue,
                        getFinalVValue(startingVValue, percents),
                        BASE_MODIFIER * deltaYOrg
                );
            }

            private void smoothTransition(double startingVValue, double finalVValue, double deltaY) {
                Interpolator interp = Interpolator.LINEAR;
                transition = new SmoothishTransition(transition, deltaY) {
                    @Override
                    protected void interpolate(double frac) {
                        scrollPane.setVvalue(
                                interp.interpolate(startingVValue, finalVValue, frac)
                        );
                    }
                };
                transition.play();
            }

            private double getFinalVValue(double startingVValue, double percents) {
                double finalVValueToSet = startingVValue + percents;
                if (finalVValueToSet>1) {
                    return 1d;
                }
                if (finalVValueToSet<0) {
                    return 0d;
                }
                return finalVValueToSet;
            }

            private double calculatePercents(boolean positive) {
                double fullHeight = scrollPane.getContent().getBoundsInLocal().getHeight();
                double viewableHeight = scrollPane.getBoundsInLocal().getHeight();
                double fullChangeInHeight = fullHeight-viewableHeight;
                double percents = baseChange /fullChangeInHeight;
                if (positive) {
                    percents = percents*-1;
                }
                return percents;
            }
        });
    }
}