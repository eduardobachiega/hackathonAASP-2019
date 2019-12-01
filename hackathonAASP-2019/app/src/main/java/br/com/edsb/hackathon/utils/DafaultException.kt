package br.com.edsb.hackathon.utils

import java.lang.Exception
import java.lang.RuntimeException

class DafaultException(descriprtion: String, e: Exception): RuntimeException(descriprtion, e)