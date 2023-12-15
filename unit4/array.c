#include <inttypes.h>
#include <stdio.h>

int32_t main(int32_t argc, char* argv[])
{
    uint64_t grocery[8] = {
        "Apple",
        "Orange",
        0,
        10,
        'a'
    };

    printf("%s\n%s\n%" PRIdPTR "\n%" PRIdPTR "\n%c\n", grocery[0], grocery[1], grocery[2], grocery[3], grocery[4]);
}
