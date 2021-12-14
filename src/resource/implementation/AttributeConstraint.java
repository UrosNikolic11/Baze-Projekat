package resource.implementation;

import resource.DBNode;
import resource.enumi.ConstraintType;

public class AttributeConstraint extends DBNode {

    private ConstraintType constraintType;

    public AttributeConstraint(String name, DBNode parent, ConstraintType constraintType) {
        this.constraintType = constraintType;
    }
}
