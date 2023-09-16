import java.lang.System;

public class Asdf
{
	public static void main(String[] args)
	{
		int x = 5;
		int y = 10;
		int z = ++x * ++y;
		System.out.println(z);

		int a = 10, b = 5, c = 1;
		int result = a + c - ++b;
		System.out.println(result);
	}
}
