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
 * A {@link walkingkooka.tree.Node} where each child is an element in the original {@link float[]}.
 */
@SuppressWarnings("lgtm[java/inconsistent-equals-and-hashcode]")
final class PojoNodeArrayFloat extends PojoNodeArray {

    static PojoNodeArrayFloat with(final PojoName name,
                                   final float[] value,
                                   final int index,
                                   final PojoNodeContext context) {
        return new PojoNodeArrayFloat(name, value, index, context);
    }

    private PojoNodeArrayFloat(final PojoName name,
                               final float[] value,
                               final int index,
                               final PojoNodeContext context) {
        super(name, value, index, context);
    }

    private float[] asFloatArray() {
        return Cast.to(this.value);
    }

    // children ..................................................................................

    @Override //
    PojoNode replaceChildren(final List<PojoNode> children) {
        final float[] newChildren = new float[children.size()];

        int i = 0;
        for (PojoNode child : children) {
            newChildren[i] = (float) child.value();
            i++;
        }

        return this.replace(newChildren);
    }

    @Override //
    PojoNode replaceChild(final PojoNode newChild) {
        final float[] newChildren = new float[this.childrenCount()];

        newChildren[newChild.index()] = (float) newChild.value();

        return this.replace(newChildren);
    }

    @Override //
    PojoNode replaceChildrenValues(final List<Object> values) {
        final float[] newChildren = new float[values.size()];

        int i = 0;
        for (Object child : values) {
            newChildren[i] = (float) child;
            i++;
        }

        return this.replace(newChildren);
    }

    private PojoNode replace(final float[] values) {
        return new PojoNodeArrayFloat(this.name(),
            values,
            this.index(),
            this.context)
            .replaceChild(this.parent());
    }

    @Override
    Object elementValue(final int index) {
        return this.asFloatArray()[index];
    }

    @Override int childrenCount() {
        return this.asFloatArray().length;
    }

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.asFloatArray());
    }

    @Override
    boolean equals0(final PojoNode other) {
        final PojoNodeArrayFloat otherArray = Cast.to(other);
        return Arrays.equals(this.asFloatArray(), otherArray.asFloatArray());
    }

    @Override
    public String toString() {
        return Arrays.toString(this.asFloatArray());
    }
}
