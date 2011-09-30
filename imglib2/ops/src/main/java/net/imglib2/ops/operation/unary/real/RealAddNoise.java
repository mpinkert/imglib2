/*

Copyright (c) 2011, Barry DeZonia.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
  * Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
  * Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
  * Neither the name of the Fiji project developers nor the
    names of its contributors may be used to endorse or promote products
    derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
*/

package net.imglib2.ops.operation.unary.real;

import java.util.Random;

import net.imglib2.ops.Real;
import net.imglib2.ops.RealOutput;
import net.imglib2.ops.UnaryOperation;


/**
 * 
 * @author Barry DeZonia
 *
 */
public final class RealAddNoise extends RealOutput implements UnaryOperation<Real,Real> {

	private final double rangeMin;
	private final double rangeMax;
	private final double rangeStdDev;
	private final Random rng;
	
	public RealAddNoise(double min, double max, double stdDev) {
		this.rangeMin = min;
		this.rangeMax = max;
		this.rangeStdDev = stdDev;
		this.rng = new Random();
		this.rng.setSeed(System.currentTimeMillis());
	}
	
	@Override
	public void compute(Real x, Real output) {
		int i = 0;
		do
		{
			double newVal = x.getReal() + (rng.nextGaussian() * rangeStdDev);
			
			if ((rangeMin <= newVal) && (newVal <=rangeMax)) {
				output.setReal(newVal);
				return;
			}
			
			if (i++ > 100)
				throw new IllegalArgumentException("noise function failing to terminate. probably misconfigured.");
		}
		while(true);
	}

	@Override
	public RealAddNoise duplicate() {
		return new RealAddNoise(rangeMin, rangeMax, rangeStdDev);
	}
}