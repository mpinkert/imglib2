/**
 * Copyright (c) 2009--2010, Stephan Preibisch & Johannes Schindelin
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
 *
 * @author Stephan Preibisch & Johannes Schindelin
 */
package mpicbg.imglib.container.imageplus;

import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ByteProcessor;

import mpicbg.imglib.container.basictypecontainer.array.FloatArray;
import mpicbg.imglib.exception.ImgLibException;
import mpicbg.imglib.type.Type;

public class FloatImagePlus<T extends Type<T>> extends ImagePlusContainer<T, FloatArray> 
{
	final ImagePlus image;	
	
	public FloatImagePlus( final ImagePlusContainerFactory factory, final int[] dim, final int entitiesPerPixel ) 
	{
		super( factory, dim, entitiesPerPixel );
		
		if ( entitiesPerPixel == 1 )
		{
			final ImageStack stack = new ImageStack( width, height );
			for ( int i = 0; i < slices; ++i )
				stack.addSlice( "", new ByteProcessor( width, height ) );
			image = new ImagePlus( "image", stack );
			image.setDimensions( channels, slices, frames );
			if ( slices > 1 )
				image.setOpenAsHyperStack( true );
			
			for ( int c = 0; c < channels; ++c )
				for ( int t = 0; t < frames; ++t )
					for ( int z = 0; z < depth; ++z )
						mirror.add( new FloatArray( ( float[] )image.getStack().getProcessor( image.getStackIndex( c + 1, z + 1 , t + 1 ) ).getPixels() ) );
		}
		else
		{
			image = null;
			for ( int i = 0; i < slices; ++i )
				mirror.add( new FloatArray( width * height * entitiesPerPixel ) );
		}
	}

	public FloatImagePlus( final ImagePlus image, final ImagePlusContainerFactory factory ) 
	{
		super( factory, ImagePlusContainer.getCorrectDimensionality(image), 1 );
		
		this.image = image;
		
		for ( int c = 0; c < channels; ++c )
			for ( int t = 0; t < frames; ++t )
				for ( int z = 0; z < depth; ++z )
					mirror.add( new FloatArray( ( float[] )image.getStack().getProcessor( image.getStackIndex( c + 1, z + 1 , t + 1 ) ).getPixels() ) );
	}

	@Override
	public void close() 
	{
		super.close();
		if ( image != null )
			image.close(); 
	}

	@Override
	public ImagePlus getImagePlus() throws ImgLibException 
	{
		if ( image == null )
			throw new ImgLibException( this, "has no ImagePlus instance, it is not a standard type of ImagePlus (" + entitiesPerPixel + " entities per pixel)" ); 
		else
			return image;
	}
}

