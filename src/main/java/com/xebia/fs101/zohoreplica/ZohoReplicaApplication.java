package com.xebia.fs101.zohoreplica;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ZohoReplicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZohoReplicaApplication.class, args);

	}




}

