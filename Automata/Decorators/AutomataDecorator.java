package Automata.Decorators;

import Automata.Exceptions.Automata_Exception;
import Automata.Figures.CountingFigure;
import CH.ifa.draw.figure.DecoratorFigure;
import CH.ifa.draw.framework.Figure;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Tiago de França Queiroz
 * Date: 02/01/14
 * <p/>
 * Copyright Tiago de França Queiroz, 2014.
 * <p/>
 * This file is part of Automata.Automata.Tests.
 * <p/>
 * Automata.Automata.Tests is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * Automata.Automata.Tests is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with Automata.Automata.Tests. If not, see <http://www.gnu.org/licenses/>.
 */
public abstract class AutomataDecorator
		extends DecoratorFigure
{
	protected CountingFigure parent;

	protected AutomataDecorator(Figure figure)
	{
		super(figure);
		if(figure instanceof AutomataDecorator)
			parent = ((AutomataDecorator) figure).getParent();
	}

	private Figure get_fComponent()
	{
		return fComponent;
	}

	private List<AutomataDecorator> getDecorators()
	{
		List<AutomataDecorator> l = new ArrayList<AutomataDecorator>();
		Figure decorated;
		Figure current = this;

		l.add(this);
		while(current instanceof AutomataDecorator)
		{
			decorated = ((AutomataDecorator) current).get_fComponent();
			if(decorated instanceof AutomataDecorator)
				l.add((AutomataDecorator) decorated);
			current = decorated;
		}
		if(l.size() > 2)
			throw new Automata_Exception("Too many decorators!");
		return l;
	}

	public FinalStateDecorator getEndDecorator()
	{
		List<AutomataDecorator> decs = getDecorators();
		for(AutomataDecorator d: decs)
		{
			System.out.println(d);
			if(d instanceof FinalStateDecorator)
				return (FinalStateDecorator) d;
		}
		return null;
	}

	public StartStateDecorator getStartDecorator()
	{
		List<AutomataDecorator> decs = getDecorators();
		for(AutomataDecorator d: decs)
			if(d instanceof StartStateDecorator)
				return (StartStateDecorator) d;
		return null;
	}

	@Override
	public void decorate(Figure figure)
	{
		if(figure instanceof AutomataDecorator)
		{
			/* There should be only one figure */
//			Enumeration<Figure> fs= figure.decompose();
//			Figure fparent = null;
//			int i = 0;
//
//			while(fs.hasMoreElements())
//			{
//				i++;
//				fparent = fs.nextElement();
//			}
//
//			if(i != 1)
//				throw new Automata_Exception("AutomataDecorator should only be used to decorate CountingFigure!");
//			if(!(fparent instanceof CountingFigure))
//				throw new Automata_Exception("AutomataDecorator should only be used to decorate CountingFigure!");
//
//			System.out.printf("I am an %s and %s is being decorated\n",
//			                  this.getClass().getSimpleName(),
//			                  figure.getClass().getSimpleName());
//			System.out.printf("\n==\nStartDec: %s\nEndDec: %s\n==\n",
//			                  ((AutomataDecorator) figure).getStartDecorator(),
//			                  ((AutomataDecorator) figure).getEndDecorator());
//			for(AutomataDecorator d: ((AutomataDecorator) figure).getDecorators())
//				System.out.printf("Dec: %s\n", d.getClass().getSimpleName());
			super.decorate(figure);
		}
		else
		{
			parent = (CountingFigure) figure;
			super.decorate(figure);
		}
	}

	public CountingFigure getParent()
	{
		return parent;
	}
}
