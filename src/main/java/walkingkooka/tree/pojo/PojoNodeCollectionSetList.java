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

import walkingkooka.collect.list.ImmutableList;
import walkingkooka.collect.list.ImmutableListDefaults;
import walkingkooka.collect.list.Lists;

import java.util.AbstractList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A read only {@link List} view of a {@link Set}
 */
final class PojoNodeCollectionSetList extends AbstractList<Object> implements ImmutableListDefaults<ImmutableList<Object>, Object> {

    static PojoNodeCollectionSetList with(final Set<Object> set) {
        return new PojoNodeCollectionSetList(set);
    }

    private PojoNodeCollectionSetList(final Set<Object> set) {
        this.set = set;
        this.list = Lists.array();
    }

    @Override
    public Object get(final int index) {
        final int size = this.size();
        if (index < 0 || index > size) {
            this.indexOutOfBoundsFail(index, size);
        }

        return index < this.list.size() ?
            this.list.get(index) :
            this.fillListAndGet(index);
    }

    private Object fillListAndGet(final int index) {
        Object element = null;

        int attempt = 0;
        for (; ; ) {
            if (attempt == 2) {
                throw new IllegalStateException("Unable to get element at " + index + " because Set was continuously updated");
            }

            attempt++;

            // try and continue using old iterator...
            Iterator<Object> iterator = this.setIterator;
            if (null == iterator) {
                this.setIterator = iterator = this.set.iterator();
            }
            try {
                while (this.list.size() <= index) {
                    if (!iterator.hasNext()) {
                        this.indexOutOfBoundsFail(index, this.size());
                    }
                    element = iterator.next();
                    this.list.add(element);
                }
                break; // while loop exhausted and element gotten!
            } catch (final ConcurrentModificationException ignore) {
                // try again! to fill
                this.setIterator = null;
                this.list.clear();
            }
        }
        return element;
    }

    private void indexOutOfBoundsFail(final int index, final int size) {
        throw new IndexOutOfBoundsException("Index " + index + " must be between 0 and " + size);
    }

    @Override
    public int size() {
        return this.set.size();
    }

    /**
     * The source {@link Set}
     */
    private final Set<Object> set;

    /**
     * The iterator providing elements
     */
    Iterator<Object> setIterator;

    /**
     * A list which eventually is being filled with entries.
     */
    private final List<Object> list;

    @Override
    public String toString() {
        return this.set.toString();
    }

    // ImmutableListDefaults............................................................................................

    @Override
    public void elementCheck(final Object element) {
        // nop
    }

    @Override
    public ImmutableList<Object> setElements(final List<Object> elements) {
        return this.setElementsFailIfDifferent(elements);
    }
}
