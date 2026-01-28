package com.zenoAppAPI.ZenoWebApp.Mappers;

public interface mapper<A,B>{
    B mapTo(A a);
    A mapFrom(B b);
}
