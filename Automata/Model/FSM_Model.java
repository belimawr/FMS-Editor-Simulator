package Automata.Model;

import Automata.Figures.CountingFigure;
import Automata.Figures.OneConnection;
import Automata.Figures.StartState;
import Automata.Figures.ZeroConnection;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.FigureChangeEvent;
import CH.ifa.draw.framework.FigureChangeListener;

import java.util.*;


/**
 * Author: Tiago de França Queiroz
 * Date: 21/12/13
 *
 * Copyright Tiago de França Queiroz, 2013.
 *
 * This file is part of Automata.
 *
 * Automata is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Automata is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Automata. If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Figures are not directly connected to nodes
 * as they are part of the view, however nodes
 * have got reference to figures, so the best
 * (and efficient) way of finding a node from
 * the figure is using a hashtable.
 */
public class FSM_Model implements FigureChangeListener
{
	private static boolean valid;

	private static FSM_Node start;

	private Map<Figure, FSM_Node> nodes;

	/*
	 * Singleton design pattern.
	 * It is used because different classes
	 * and objects need to access the same data
	 * structure.
	 */
	public static final FSM_Model me = new FSM_Model();

	public FSM_Model()
	{
		valid = false;
		start = null;

		nodes = new HashMap<Figure, FSM_Node>();
	}

	public void insert(Figure key, FSM_Node node)
	{
		nodes.put(key, node);
		/* Add this as a listener to the figure*/
		key.addFigureChangeListener(this);
		System.out.printf("FSM_Model: (%s, %s) inserted\n", key, node);
	}

	public void replace(Figure fold, Figure fnew)
	{
		FSM_Node node = nodes.remove(fold);
		node.setMyFigure(fnew);
		nodes.put(fnew, node);

		System.out.printf("Replacing: %s -> %s\n", fold, fnew);
	}

	public boolean connectOne(Figure start, Figure end)
	{
		FSM_Node nstart, nend;

		nstart = nodes.get(start);
		nend = nodes.get(end);

		nstart.setOne(nend);

		System.out.printf("1: \"%s\"->\"%s\"\n", start, end);
		return true;
	}

	public boolean connectZero(Figure start, Figure end)
	{
		FSM_Node nstart, nend;

		nstart = nodes.get(start);
		nend = nodes.get(end);

		nstart.setZero(nend);

		System.out.printf("1: \"%s\"->\"%s\"\n", start, end);
		return true;
	}

	public boolean isValid()
	{
		if(start == null)
		{
			valid = false;
			return false;
		}

		valid = true;
		Collection<FSM_Node> states = nodes.values();
		if(valid)
			return true;
		else
		{
			for(FSM_Node n: states)
				if(!n.isValid())
				{
					valid = false;
					return false;
				}
		}
		return valid;
	}

	public static FSM_Model getInstance()
	{
		return me;
	}

	public FSM_Node getNode(Figure f)
	{
		return nodes.get(f);
	}

	public void print_debug()
	{
		int i = 0;
		Collection<FSM_Node> my_nodes = nodes.values();


		for(FSM_Node n: my_nodes)
				System.out.printf("[%2d] - %s\n", i++, n);

		System.out.printf("Is model valid: %s\n", isValid());
	}


	/*
	 * FigureChangeListener methods.
	 * The only one used is figureRemoved
	 * to remove the node from the data structure.
	 */
	@Override
	public void figureChanged(FigureChangeEvent e)
	{
	}

	@Override
	public void figureInvalidated(FigureChangeEvent e)
	{
	}

	/*
	 * This method removes from the data structure the
	 * figure that was deleted from the drawing.
	 *
	 * When a connection is removed, its links are
	 * also removed as well
	 */
	@Override
	public void figureRemoved(FigureChangeEvent e)
	{
		Figure f = e.getFigure();

		/* If it's a State, just remove it */
		if(f instanceof CountingFigure || f instanceof StartState)
		{
			nodes.remove(f);
			System.out.printf("Figure %s was removed\n", e.getFigure());
		}
		/* If it's a connection remove its link */
		else if(f instanceof OneConnection)
		{
			OneConnection of = (OneConnection) f;
			Figure start = of.startFigure();
			FSM_Node start_node = getNode(start);

			if(start_node != null)
				start_node.setOne(null);
			System.out.printf("OneConnection %s -> %s removed\n", start, ((OneConnection) f).endFigure());
		}
		else if(f instanceof ZeroConnection)
		{
			ZeroConnection of = (ZeroConnection) f;
			Figure start = of.startFigure();
			FSM_Node start_node = getNode(start);

			if(start_node != null)
				start_node.setZero(null);
			System.out.printf("ZeroConnection %s -> %s removed\n", start, ((ZeroConnection) f).endFigure());
		}
		else
		{
			System.out.printf("I don't know what to do with %s\n", f);
		}
	}

	@Override
	public void figureRequestRemove(FigureChangeEvent e)
	{
	}

	@Override
	public void figureRequestUpdate(FigureChangeEvent e)
	{
	}
}
