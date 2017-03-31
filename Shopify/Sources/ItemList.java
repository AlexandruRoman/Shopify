import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by Alex on 12/28/2016.
 */

public abstract class ItemList {
    static class Node<E>{
        E item;
        Node<E> next;
        Node<E> prev;
    }

    public ListIterator<Item> listIterator()  { return new ItemIterator(); }
    public ListIterator<Item> listIterator(int index)  {
        Iterator it =  new ItemIterator();
        int i = 0;
        Item aux;
        while(i<index)
        {
            aux = (Item)it.next();
            i++;
        }
        return (ItemIterator)it;
    }

    private class ItemIterator implements ListIterator<Item>{

        private Node current = begin;
        @Override
        public boolean hasNext() {
            return current.next != last;
        }

        @Override
        public Item next() {
            if(!hasNext()) return null;
            current = current.next;
            return (Item)current.item;
        }

        @Override
        public boolean hasPrevious() {return current.prev != begin;}

        @Override
        public Item previous() {
            if(!hasPrevious()) return null;
            current = current.prev;
            return (Item)current.item;
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove() {
            current.prev.next = current.next;
            current.next.prev = current.prev;
            current = current.next;
        }

        @Override
        public void set(Item item) {

        }

        @Override
        public void add(Item item) {

        }
    }
    public int len;
    private Node begin;
    private Node last;
    private Comparator comparator;

    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }

    public ItemList()
    {
        begin = new Node<>();
        last = new Node<>();
        begin.next = last;
        last.prev = begin;
        len = 0;
        this.comparator = new ItemComparatorByName();
    }

    public ItemList(Comparator comparator)
    {
        begin = new Node<>();
        last = new Node<>();
        begin.next = last;
        last.prev = begin;
        len = 0;
        this.comparator = comparator;
    }

    public boolean add(Item item)
    {
        Node nou = new Node<>();
        nou.item = new Item(item);
        if(comparator != null) {
            Node aux = begin.next;
            while (aux != last) {
                if (comparator.compare(nou.item, aux.item) < 0) {
                    nou.next = aux;
                    nou.prev = aux.prev;
                    aux.prev = nou;
                    nou.prev.next = nou;
                    break;
                }
                aux = aux.next;
            }
            if (aux == last) {
                nou.prev = last.prev;
                nou.next = last;
                last.prev = nou;
                nou.prev.next = nou;
            }
        }
        else
        {
            nou.next = last;
            nou.prev = last.prev;
            last.prev.next = nou;
            last.prev = nou;
        }
        len++;
        return true;
    }
    public boolean addAll(Collection<? extends Item> c)
    {
        Iterator index = c.iterator();
        while(index.hasNext())
        {
            add((Item)index.next());
        }
        return true;
    }
    public Item getItem(int index)
    {
        if(index == 0)
            return null;
        Iterator it =  new ItemIterator();
        int i = 0;
        Item aux = (Item)begin.item;
        while(i<index)
        {
            aux = (Item)it.next();
            i++;
        }
        return aux;
    }
    public Node<Item> getNode(int index)
    {
        int i = 0;
        Node aux = begin.next;
        while(i < index)
        {
            aux = aux.next;
            i++;
        }
        return aux;
    }
    public int indexOf(Item item)
    {
        int i=0;
        Iterator it = new ItemIterator();
        Item aux;
        while(it.hasNext())
        {
            aux = (Item)it.next();
            i++;
            if(aux.getId() == item.getId())
                return i;
        }
        if(!it.hasNext())
            return 0;
        return i;
    }
    public int indexOf(Node<Item> node)
    {
        int i=0;
        Node<Item> aux = begin.next;
        while(aux.item.getId() == node.item.getId())
        {
            i++;
            aux = aux.next;
        }
        return i;
    }

    public boolean contains(Node<Item> node)
    {
        Node<Item> aux = begin.next;
        while(aux != last)
        {
            if(aux.item.getId() == node.item.getId())
                return true;
        }
        return false;
    }

    public boolean contains(Item item)
    {
        Node<Item> aux = begin.next;
        while(aux != last)
        {
            if(aux.item.getId() == item.getId())
                return true;
        }
        return false;
    }
    public Item remove(int index)
    {
        int i = 0;
        Node<Item> aux = begin.next;
        while(i < index)
        {
            aux = aux.next;
            i++;
        }
        aux.prev.next = aux.next;
        aux.next.prev = aux.prev;
        len--;
        return aux.item;
    }
    public boolean remove(Item item)
    {
        if(isEmpty())
            return false;
        if(item == null)
            return false;
        Node<Item> aux = begin.next;
        if(aux.item.getId() == item.getId()) {
            aux.prev.next = aux.next;
            aux.next.prev = aux.prev;
            len--;
            return true;
        }
        while(aux.next != last)
        {
            aux = aux.next;
            if(aux.item.getId() == item.getId()) {
                aux.prev.next = aux.next;
                aux.next.prev = aux.prev;
                len--;
                return true;
            }
        }
        return false;
    }
    public boolean removeAll(Collection<? extends Item> collection)
    {
        Iterator it = collection.iterator();
        while(it.hasNext())
        {
            remove((Item)it.next());
        }
        return true;
    }
    public boolean isEmpty()
    {
        return begin.next == last;
    }
    public Double getTotalPrice()
    {
        Double s = 0.0;
        Iterator it = listIterator();
        while(it.hasNext())
        {
            s += ((Item)it.next()).getPrice();
        }
        return s;
    }

    public String toString(Comparator comparator)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        String s = "[";
        if(!isEmpty()) {
            DoublyLinkedList aux = new DoublyLinkedList(comparator);
            Iterator iterator = listIterator();
            while (iterator.hasNext())
                aux.add((Item) iterator.next());

            iterator = aux.listIterator();
            Item aux2 = (Item) iterator.next();
            s += aux2.getName() + ";" + aux2.getId() + ";" + String.format("%.2f", aux2.getPrice());
            while (iterator.hasNext()) {
                aux2 = (Item) iterator.next();
                s += ", " + aux2.getName() + ";" + aux2.getId() + ";" + String.format("%.2f", aux2.getPrice());
            }
        }
        s += "]";
        return s;
    }

    public void show()
    {
        Node aux = last.prev;
        while(aux != begin)
        {
            System.out.println(((Item)aux.item).getName());
            aux = aux.prev;
        }
    }




}
