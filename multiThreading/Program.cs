//Cullen McDevitt
//February 27, 2014
//Multi Threading Program

using System;
using System.Threading;

public class MultiT
{
    static Mutex mu;
    public int n=1;
    public int m=0;

    public static void Main(String[] args)
    {
        mu = new Mutex(true);

        MultiT tm = new MultiT();
        tm.ReadIn();
        Thread t1 = new Thread(new ThreadStart(tm.t1Start));
        Thread t2 = new Thread(new ThreadStart(tm.t2Start));
        Thread t3 = new Thread(new ThreadStart(tm.t3Start));
        Thread t4 = new Thread(new ThreadStart(tm.t4Start));
        t1.Start(); 
        t2.Start();
        t3.Start();   
        t4.Start();  

        mu.ReleaseMutex();
        t1.Join();
        t2.Join();
        t3.Join();
        t4.Join();
        Console.Write("\nPress enter to exit");
        Console.Read();
    }
    public void ReadIn()
    {
        string l;
        Console.WriteLine("Input number to find all primes lower than that number.");
        l = Console.ReadLine();
        m = Convert.ToInt32(l);
    }
    public void t1Start()
    {
        int k;
        
        if (!mu.WaitOne())
        {
            Console.WriteLine("Critical section in use. 1");
        }
        else
        {
            //Console.WriteLine("1 has mutex");
            k=getN();
            mu.ReleaseMutex();
            if (k == -1) {}
            else
            {
                prime(k);
                t1Start();
            }
        }
    }

    public void t2Start()
    {
        int k;

        if (!mu.WaitOne())
        {
            Console.WriteLine("Critical section in use. 2");
        }
        else
        {
            //Console.WriteLine("2 has mutex");
            k = getN();
            mu.ReleaseMutex();
            if (k == -1) {}
            else
            {
                prime(k);
                t2Start();
            }
        }
    }

    public void t3Start()
    {
        int k;

        if (!mu.WaitOne())
        {
            Console.WriteLine("Critical section in use. 3");
        }
        else
        {
            //Console.WriteLine("3 has mutex");
            k = getN();
            mu.ReleaseMutex();
            if (k == -1) {}
            else
            {
                prime(k);
                t3Start();
            }
        }
    }

    public void t4Start()
    {
        int k;

        if (!mu.WaitOne())
        {
            Console.WriteLine("Critical section in use. 4");
        }
        else
        {
            //Console.WriteLine("4 has mutex");
            k = getN();
            mu.ReleaseMutex();
            if (k == -1) {}
            else
            {
                prime(k);
                t4Start();
            }
        }
    }
    public int getN()
    {
        if (m == n){return -1;}
        else
        {
            n++;
            return n;            
        }
    }
    public void prime(int k)
    {
        int i=2;
        bool j=false;
        do
        {

            if (i == k)
            {
                Console.Write(k + " ");
                j = true;
            }
            else if (k % i == 0){j=true;}
            i++;
        }
        while(j!=true);
    }
}