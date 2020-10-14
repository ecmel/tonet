package tonet;

public class OpenViduCollection<T>
{
    private int numberOfElements;
    private T[] content;

    public int getNumberOfElements()
    {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements)
    {
        this.numberOfElements = numberOfElements;
    }

    public T[] getContent()
    {
        return content;
    }

    public void setContent(T[] content)
    {
        this.content = content;
    }
}
