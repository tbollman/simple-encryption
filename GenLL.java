/*
 *
 * Timothy Bollman
 *
 * */

public class GenLL <T>
{
    private class ListNode
    {
        private T data;
        private ListNode link;
        public ListNode(T aData, ListNode aLink)
        {
            data = aData;
            link = aLink;
        }
    }
    private ListNode head;
    private ListNode curr;
    private ListNode prev;
    public GenLL()
    {
        head = curr = prev = null;
    }
    public T getCurrent()
    {
        if(curr != null)
            return curr.data;
        else
            return null;
    }
    public void goToNext()
    {
        if(curr == null)
        {
            return;
        }
        prev = curr;
        curr = curr.link;
    }
    public void reset()
    {
        curr = head;
    }
    public void goToPrev()
    {
        reset();
        while(curr.link != prev)
        {
            curr = curr.link;
        }
        prev.link = prev;
        prev = curr;
        curr = prev.link;
    }
    public void insert(T aData)
    {
        ListNode newNode = new ListNode(aData, null);
        if(head == null)
        {
            head = newNode;
            curr = head;
            return;
        }
        ListNode temp = head;
        while(temp.link != null)
            temp = temp.link;
        temp.link = newNode;
    }
    public void deleteCurrent()
    {
        if(curr != null && prev != null)
        {
            prev.link = curr.link;
            curr = curr.link;
        }
        else if(curr != null)//curr is at head
            head = head.link;
    }
    public boolean hasMore()
    {
        return curr != null;
    }
    public void print()
    {
        ListNode temp = head;
        while(temp != null)
        {
            System.out.println(temp.data);
            temp = temp.link;
        }
    }
    public void goToItem(T aData)
    {
        ListNode temp = this.findNodeWith(aData);
        if(temp == null)
            return;
        this.reset();
        while(this.hasMore() && curr != temp)
        {
            this.goToNext();
        }
    }
    private ListNode findNodeWith(T aData)
    {
        ListNode temp = head;
        while(temp != null)
        {
            if(temp.data.equals(aData))
                return temp;
            temp = temp.link;
        }
        return null;
    }
    public int count()
    {
        int count = 0;
        reset();
        ListNode temp = head;
        while(temp != null)
        {
            count++;
            temp = temp.link;
        }
        return count;
    }
}
