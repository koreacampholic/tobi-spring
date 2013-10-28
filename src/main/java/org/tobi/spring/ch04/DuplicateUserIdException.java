package org.tobi.spring.ch04;

import org.springframework.dao.DuplicateKeyException;

public class DuplicateUserIdException extends Exception {

	public DuplicateUserIdException(DuplicateKeyException e) {
		// TODO Auto-generated constructor stub
	}

}
