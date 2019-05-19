package com.him.poc.avrorpc;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.netty.NettyServer;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.apache.avro.util.Utf8;

import example.proto.Mail;
import example.proto.Message;

/**
 * Hello world!
 *
 */
public class App {
	public static class MailImpl implements Mail {
		// in this simple example just return details of the message
		public Utf8 send(Message message) {
			System.out.println("Sending message");
			return new Utf8("Sending message to " + message.getTo().toString() + " from " + message.getFrom().toString()
					+ " with body " + message.getBody().toString());
		}
	}

	private static Server server;

	private static void startServer() throws IOException {
		server = new NettyServer(new SpecificResponder(Mail.class, new MailImpl()),
				new InetSocketAddress("127.0.0.1", 65111));
		// the server implements the Mail protocol (MailImpl)
		server.start();
	}

	public static void main(String[] args) throws IOException, AvroRemoteException {

		System.out.println("Starting server");
		// usually this would be another app, but for simplicity
		startServer();
		System.out.println("Server started");
		// server.close();
	}
}
