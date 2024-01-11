#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main(void)
{
    int n;
    long double iterations;
    long double start;
    long double duration;

    start = time(NULL);
    srand(start);

    n = 0;
    iterations = 0;
    while ( n != 42 )
    {
        n = rand();
        iterations++;
    }

    duration = (long double)time(NULL) - start;
    printf("n is 42 after %.0Lf iterations and %.0Lf seconds (average of %.0Lf iterations per second), RAND_MAX is %d\n",
        iterations, duration, iterations / duration, RAND_MAX);
}

