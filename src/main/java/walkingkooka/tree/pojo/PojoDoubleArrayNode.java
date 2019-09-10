/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.tree.pojo;

import walkingkooka.Cast;

import java.util.Arrays;
import java.util.List;

/**
 * A {@link walkingkooka.tree.Node} where each child is an element in the original {@link double[]}.
 */
final class PojoDoubleArrayNode extends PojoArrayNode {

    static PojoDoubleArrayNode with(final PojoName name,
                                    final double[] value,
                                    final int index,
                                    final PojoNodeContext context) {
        return new PojoDoubleArrayNode(name, value, index, context);
    }

    private PojoDoubleArrayNode(final PojoName name,
                                final double[] value,
                                final int index,
                                final PojoNodeContext context) {
        super(name, value, index, context);
    }

    private double[] valueAsDoubleArray() {
        return Cast.to(this.value);
    }

    // children ..................................................................................

    @Override
    final PojoNode replaceChildren(final List<PojoNode> children){
        final double[] newChildren = new double[children.size()];

        int i = 0;
        for (PojoNode child : children) {
            newChildren[i] = (double) child.value();
            i++;
        }

        return this.replace(newChildren);
    }

    @Override
    final PojoNode replaceChild(final PojoNode newChild) {
        final double[] newChildren = new double[this.childrenCount()];

        newChildren[newChild.index()] = (double) newChild.value();

        return this.replace(newChildren);
    }

    @Override
    PojoNode replaceChildrenValues(final List<Object> values) {
        final double[] newChildren = new double[values.size()];

        int i = 0;
        for (Object child : values) {
            newChildren[i] = (double) child;
            i++;
        }

        return this.replace(newChildren);
    }

    private PojoNode replace(final double[] values) {
        return new PojoDoubleArrayNode(this.name(),
                values,
                this.index(),
                this.context)
                .replaceChild(this.parent());
    }

    @Override
    Object elementValue(final int index) {
        return this.valueAsDoubleArray()[index];
    }

    @Override
    final int childrenCount() {
        return this.valueAsDoubleArray().length;
    }

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.valueAsDoubleArray());
    }

    @Override
    boolean equals0(final PojoNode other) {
        final PojoDoubleArrayNode otherArray = Cast.to(other);
        return Arrays.equals(this.valueAsDoubleArray(), otherArray.valueAsDoubleArray());
    }

    @Override
    final public String toString() {
        return Arrays.toString(this.valueAsDoubleArray());
    }
}
