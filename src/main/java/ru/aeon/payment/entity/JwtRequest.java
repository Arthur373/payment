package ru.aeon.payment.entity;

import lombok.Data;

/**
 * Simple JavaBean object that represents a JwtRequest.
 *
 * @author Arthur
 * @version 1.0
 */
@Data
public class JwtRequest  {

	private String email;
	private String password;
}