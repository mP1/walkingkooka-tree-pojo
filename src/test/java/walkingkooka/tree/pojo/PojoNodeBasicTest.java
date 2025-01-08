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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.map.Maps;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class PojoNodeBasicTest extends PojoNodeTestCase<PojoNodeBasic, Integer> {

    @Test
    public void testNull() {
        final PojoNodeBasic node = this.createPojoNode(null);
        this.checkEquals(Maps.empty(), node.attributes());
    }

    @Test
    public void testParent() {
        this.parentMissingCheck(this.createNode());
    }

    @Test
    public void testChildren() {
        this.childrenOf(123);
    }

    @Test
    public void testChildrenOfNull() {
        this.childrenOf(null);
    }

    @Test
    public void testChildrenOfString() {
        this.childrenOf("abc123");
    }

    private void childrenOf(final Object value) {
        final PojoNodeBasic node = this.createPojoNode(value);
        this.childrenAndCheckNames(node);
        this.parentMissingCheck(node);
    }

    @Test
    public void testChildrenSetFails() {
        assertThrows(UnsupportedOperationException.class, () -> {
            this.createNode().setChildren(Lists.array());
        });
    }

    @Test
    public void testEqualsDifferentValue() {
        this.checkNotEquals(this.createPojoNode(456));
    }

    @Test
    public void testEqualsDifferentValueType() {
        this.checkNotEquals(this.createPojoNode(6.7));
    }

    @Test
    public void testSameValueDifferentType() {
        this.checkNotEquals(this.createPojoNode((byte) 123));
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createNode(), "123");
    }

    @Override
    PojoNodeBasic createPojoNode() {
        return this.createPojoNode(this.value());
    }

    @Override
    Integer value() {
        return 123;
    }

    @Override
    Integer differentValue() {
        return 456;
    }

    @Override
    void checkValue(final Integer expected, final Integer actual) {
        this.checkEquals(expected, actual);
    }

    private PojoNodeBasic createPojoNode(final Object value) {
        return Cast.to(PojoNode.wrap(PojoName.property("root"),
            value,
            ReflectionPojoNodeContext.with()));
    }

    @Override
    Class<PojoNodeBasic> pojoNodeType() {
        return PojoNodeBasic.class;
    }
}
