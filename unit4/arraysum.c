/* A simple program making use of POSIX threads and mutexes to quickly calculate the sum of an array */
#include <stdio.h>
#include <pthread.h>

#define ARRAY_SIZE(a) (sizeof(a)/sizeof(a[0]))

/* Maximum number of threads */
#define MAX_THREADS 4

int i;
int a[] = { 1, 5, 7, 10, 12, 14, 15, 18, 20, 22, 25, 27, 30, 64, 110, 220 };
int sum[MAX_THREADS] = { 0 };

int total_sum = 0;
pthread_mutex_t array_mutex;

void *sum_array(void *arg)
{
	int len = ARRAY_SIZE(a) / MAX_THREADS;
	long thr_num = (long)arg;
	int start, end;

	start = len * thr_num;
	end = start + len;

	/* Calculate the sum for this thread's set */
	sum[thr_num] = 0;
	for (i = start; i < end; i += 2)
		sum[thr_num] += (a[i] + a[i+1]);
		
	pthread_mutex_lock(&array_mutex);
	total_sum += sum[thr_num];
	pthread_mutex_unlock(&array_mutex);
}
/* Set up the threads and wait for them to finish, then exit */
int main()
{
	pthread_t threads[MAX_THREADS];
	pthread_mutex_init(&array_mutex, NULL);
	/* Create 4 threads */
	for (i = 0; i < MAX_THREADS; i++)
		pthread_create(&threads[i], NULL, sum_array, (void *)i);

	/* Wait for every thread to finish */
	for (i = 0; i < MAX_THREADS; i++)
		pthread_join(threads[i], NULL);

	printf("sum is %d\n", total_sum);
	pthread_mutex_destroy(&array_mutex);

	return 0;
}
