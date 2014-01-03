package Automata.Decorators;

import CH.ifa.draw.figure.ArrowTip;
import CH.ifa.draw.figure.LineDecoration;
import CH.ifa.draw.storable.StorableInput;
import CH.ifa.draw.storable.StorableOutput;

import java.awt.*;
import java.io.IOException;

/**
 * Author: Tiago de França Queiroz
 * Date: 03/01/14
 * <p/>
 * Copyright Tiago de França Queiroz, 2014.
 * <p/>
 * This file is part of Automata.Automata.Decorators.
 * <p/>
 * Automata.Automata.Decorators is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * Automata.Automata.Decorators is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with Automata.Automata.Decorators. If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * The attributes are private, so I had to
 * copy the whole class instead of extending it....
 */
public class BorderArrowTip
		implements LineDecoration
{
	private double  fAngle;         // pointiness of arrow
	private double  fOuterRadius;
	private double  fInnerRadius;

	/*
	 * Serialization support.
	 */
	private static final long serialVersionUID = -3459171428373823638L;

	public BorderArrowTip()
	{
		fAngle = 0.40;//0.35;
		fOuterRadius = 8;//15;
		fInnerRadius = 8;//12;
	}

	/**
	 * Constructs an arrow tip with the given angle and radius.
	 */
	public BorderArrowTip(double angle, double outerRadius, double innerRadius)
	{
		fAngle = angle;
		fOuterRadius = outerRadius;
		fInnerRadius = innerRadius;
	}

	private void addPointRelative(Polygon shape, int x, int y, double radius, double angle)
	{
		shape.addPoint(
				x + (int) (radius * Math.cos(angle)),
				y - (int) (radius * Math.sin(angle)));
	}

	/**
	 * Draws the arrow tip in the direction specified by the given two
	 * points..
	 */
	@Override
	public void draw(Graphics g, int x1, int y1, int x2, int y2)
	{
		Color old = g.getColor();
		g.setColor(Color.white);

		Polygon p = outline(x1, y1, x2, y2);

		Rectangle r = p.getBounds();
		r.grow(1, 1);
		g.fillRect(r.x, r.y, r.width, r.height);

		g.setColor(old);
		g.fillPolygon(p.xpoints, p.ypoints, p.npoints);
	}

	private Polygon outline(int x, int y, double direction)
	{
		Polygon shape = new Polygon();

		shape.addPoint(x, y);
		addPointRelative(shape, x, y, fOuterRadius, direction - fAngle);
		addPointRelative(shape, x, y, fInnerRadius, direction);
		addPointRelative(shape, x, y, fOuterRadius, direction + fAngle);
		shape.addPoint(x,y); // Closing the polygon (TEG 97-04-23)
		return shape;
	}

	/**
	 * Calculates the outline of an arrow tip.
	 */
	public Polygon outline(int x1, int y1, int x2, int y2)
	{
		double dir = Math.PI/2 - Math.atan2(x2-x1, y1-y2);
		return outline(x1, y1, dir);
	}

	/**
	 * Reads the arrow tip from a StorableInput.
	 */
	@Override
	public void read(StorableInput dr) throws IOException
	{
	}

	/**
	 * Stores the arrow tip to a StorableOutput.
	 */
	@Override
	public void write(StorableOutput dw)
	{
	}

}
