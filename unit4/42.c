#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main(void)
{
    int n;
    long double iterations;

    srand(time(NULL));

    n = 0;
    iterations = 0;
    while ( n != 42 )
    {
        n = rand();
        iterations++;
    }

    printf("n is 42 after %.0Lf iterations\n", iterations);
}

