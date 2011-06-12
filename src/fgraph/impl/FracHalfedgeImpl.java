package fgraph.impl;

import fgraph.GraphFactory;
import fgraph.Halfedge;
import fgraph.Halfedges;
import fgraph.Node;

/**
 * This class models...
 * Author: nnombela@gmail.com
 * Date: 1/06/11
 */
public class FracHalfedgeImpl extends HalfedgeImpl {
    protected Node down;

    @Override
    public GraphFactory factory() {
        return GraphFactory.get("fractal-graph");
    }

    public void free() {
        free(down);
        super.free();
    }

    public Node down() {
        return down;
    }

    @Override
    public void setDown(Node down) {
        this.down = Checker.setDown(this, down);
    }
}
