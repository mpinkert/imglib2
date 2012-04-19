/**
 * Copyright (c) 2009--2012, ImgLib2 developers
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.  Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials
 * provided with the distribution.  Neither the name of the Fiji project nor
 * the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package tests;

import net.imglib2.RealPoint;
import net.imglib2.RealRandomAccess;
import net.imglib2.type.numeric.complex.ComplexDoubleType;
import net.imglib2.type.numeric.integer.UnsignedByteType;

/**
 * A RealRandomAccess that procedurally generates values (iteration count)
 * for the mandelbrot set.
 *
 * @author Stephan Saalfeld <saalfeld@mpi-cbg.de>
 */
public class JuliaRealRandomAccess extends RealPoint implements RealRandomAccess< UnsignedByteType >
{
	final protected UnsignedByteType t;
	final protected ComplexDoubleType a;
	final protected ComplexDoubleType c;
	int maxIterations;
	double maxAmplitude;

	public JuliaRealRandomAccess()
	{
		super( 2 );
		t = new UnsignedByteType();
		a = new ComplexDoubleType();
		c = new ComplexDoubleType();
		maxIterations = 50;
		maxAmplitude = 4096;
	}
	
	public JuliaRealRandomAccess(
			final ComplexDoubleType c,
			final int maxIterations,
			final int maxAmplitude )
	{
		super( 2 );
		t = new UnsignedByteType();
		a = new ComplexDoubleType();
		this.c = c;
		this.maxIterations = maxIterations;
		this.maxAmplitude = maxAmplitude;
	}
	
	public void setC( final ComplexDoubleType c )
	{
		this.c.set( c );
	}
	
	public void setC( final double r, final double i )
	{
		c.set( r, i );
	}
	
	public void setMaxIterations( final int maxIterations )
	{
		this.maxIterations = maxIterations;
	}
	
	public void setMaxAmplitude( final int maxAmplitude )
	{
		this.maxAmplitude = maxAmplitude;
	}
	
	final private int julia()
	{
		int i = 0;
		double v = 0;
		a.set( position[ 0 ], position[ 1 ] );
		while ( i < maxIterations && v < 4096 )
		{
			a.mul( a );
			a.add( c );
			v = a.getPowerDouble();
			++i;
		}
		return i < 0 ? 0 : i > 255 ? 255 : i;
	}

	@Override
	public UnsignedByteType get()
	{
		t.set( julia() );
		return t;
	}

	@Override
	public JuliaRealRandomAccess copyRealRandomAccess()
	{
		return copy();
	}

	@Override
	public JuliaRealRandomAccess copy()
	{
		final JuliaRealRandomAccess copy = new JuliaRealRandomAccess();
		copy.setPosition( this );
		return copy;
	}
}