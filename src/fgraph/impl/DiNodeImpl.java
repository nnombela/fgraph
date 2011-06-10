package fgraph.impl;

import fgraph.*;

/**
 * This class models...
 * Author: nnombela@gmail.com
 * Date: 2/06/11
 */
public class DiNodeImpl extends DuoContainerImpl<Halfedge> implements Node {
    protected Halfedge up;
    protected Graph down;

    @Override
    public GraphFactory factory() {
        return GraphFactory.get("digraph");
    }

    public DiNodeImpl() {
        super(HalfedgesImpl.class);
    }

    public void free() {
        if (up != null) {
            up.free();
        }
        if (down != null) {
            down.free();
        }
        super.free();
    }

    @Override
    public Halfedges halfedges(Halfedge.Direction direction) {
        return (Halfedges)containers[direction.ordinal()];
    }


    @Override
    public Halfedge.Direction direction() {
        return null;
    }

    @Override
    public Node reverse() {
        return this;
    }

    public Node inverse() {
        return up().direct().down();
    }

    public Type type() {
        return Type.node;
    }

    public Duality duality() {
        return ((Nodes)owner).duality();
    }

    public Graph down() {
        return down;
    }

    public Halfedge up() {
        return up;
    }

    @Override
    public void setDown(Graph down) {
        this.down = Checker.setDown(this, down);
    }

    @Override
    public void setUp(Halfedge up) {
        this.up = Checker.setUp(this, up);
    }
}
