/*
 * #%L
 * ImgLib: a general-purpose, multidimensional image processing library.
 * %%
 * Copyright (C) 2009 - 2012 Stephan Preibisch, Stephan Saalfeld, Tobias
 * Pietzsch, Albert Cardona, Barry DeZonia, Curtis Rueden, Lee Kamentsky, Larry
 * Lindsey, Johannes Schindelin, Christian Dietz, Grant Harris, Jean-Yves
 * Tinevez, Steffen Jaensch, Mark Longair, Nick Perry, and Jan Funke.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package mpicbg.imglib.algorithm.function;

import mpicbg.imglib.function.Function;
import mpicbg.imglib.type.numeric.NumericType;

/**
 * TODO
 *
 * @author Stephan Preibisch
 */
public class SubtractNorm< A extends NumericType<A> > implements Function< A, A, A >
{
	final A normalizationFactor;
	
	public SubtractNorm( final A normalizationFactor )
	{
		this.normalizationFactor = normalizationFactor;
	}
	
	@Override
	public void compute( final A input1, final A input2, final A output )
	{
		output.set( input1 );
		output.sub( input2 );
		output.mul( normalizationFactor );
	}

}
