package CCMR.Models.Types;

import java.util.ArrayList;

public class Row<T> extends ArrayList<T>
{
	private static final long serialVersionUID = 1923739472928L;
	
	public void Add(T... items) { for (T item : items) add(item); }
	public void Remove(T... items) { for (T item : items) remove(item); }

	public long Count() { return this.stream().count(); }
	public boolean IsEmpty() { return this.stream().count() == 0; }
}